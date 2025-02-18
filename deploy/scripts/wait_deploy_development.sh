#!/bin/bash
set -e

# Railway API Configuration
RAILWAY_API="https://backboard.railway.app/graphql/v2"
PROJECT_ID="5824a4b7-2bd9-45dd-96a5-188788ca1720"
SERVICE_ID="cb31c136-511d-4fb7-a7da-5b0e278e1677"
ENVIRONMENT_ID="7d111a97-b938-4184-a351-a8237e553c98"

# Configuration
MAX_WAIT_MINUTES=2      # Maximum wait time in minutes
CHECK_INTERVAL=10       # Time between status checks in seconds
MAX_ATTEMPTS=$((MAX_WAIT_MINUTES * 60 / CHECK_INTERVAL))  # Convert minutes to attempts

# Function to check the latest deployment status
check_deployment_status() {
  RESPONSE=$(curl -s -X POST "$RAILWAY_API" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $RAILWAY_API_KEY" \
    --data-raw '{
      "query": "query {
        deployments(input: {
          projectId: \"'"$PROJECT_ID"'\",
          serviceId: \"'"$SERVICE_ID"'\",
          environmentId: \"'"$ENVIRONMENT_ID"'\"
        }, first: 1) {
          edges {
            node { status }
          }
        }
      }"
    }')

  echo "$RESPONSE" | jq -r '.data.deployments.edges[0].node.status' || echo "$RESPONSE"
}

# Wait for the deployment to be successful
echo "Waiting for Railway deployment to finish (Timeout: $MAX_WAIT_MINUTES minutes)..."
for ((i=1; i<=MAX_ATTEMPTS; i++)); do
  STATUS=$(check_deployment_status)
  echo "[$((i * CHECK_INTERVAL / 60)) min] Current Status: $STATUS"

  if [[ "$STATUS" == "SUCCESS" ]]; then
    echo "✅ Deployment finished successfully!"
    exit 0
  fi

  if [[ $i -eq $MAX_ATTEMPTS ]]; then
    echo "⏳ Timeout reached! Deployment is still not finished. Exiting..."
    exit 1
  fi

  echo "⏳ Still waiting... Checking again in $CHECK_INTERVAL seconds."
  sleep $CHECK_INTERVAL
done
