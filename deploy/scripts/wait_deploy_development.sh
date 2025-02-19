#!/bin/bash
set -e

# Railway API Configuration
RAILWAY_API="https://backboard.railway.app/graphql/v2"

# Configuration
MAX_WAIT_MINUTES=5      # Maximum wait time in minutes
CHECK_INTERVAL=10       # Time between status checks in seconds
MAX_ATTEMPTS=$((MAX_WAIT_MINUTES * 60 / CHECK_INTERVAL))  # Convert minutes to attempts

# Function to check the latest deployment status
check_deployment_status() {
  RESPONSE=$(curl -s -X POST "$RAILWAY_API" \
    -H "Content-Type: application/json" \
    -H "Authorization: Bearer $RAILWAY_API_KEY" \
    --data-raw "{\"query\":\"{ deployments(input: { \
        projectId: \\\"$PROJECT_ID\\\", \
        serviceId: \\\"$SERVICE_ID\\\", \
        environmentId: \\\"$ENVIRONMENT_ID\\\" \
        }, first:1) { edges { node { status } } } }\"}")

  echo "$RESPONSE" | jq -r '.data.deployments.edges[0].node.status'
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
