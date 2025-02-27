#!/bin/bash
set -e

# Railway API Configuration
RAILWAY_API="https://backboard.railway.app/graphql/v2"

# Configuration
CHECK_INTERVAL=10  # Time between status checks in seconds

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

# Wait indefinitely for the deployment to be successful
echo "Waiting indefinitely for Railway deployment to finish..."
while true; do
  STATUS=$(check_deployment_status)
  echo "Current Status: $STATUS"

  if [[ "$STATUS" == "SUCCESS" ]]; then
    echo "✅ Deployment finished successfully!"
    exit 0
  fi

  echo "⏳ Still waiting... Checking again in $CHECK_INTERVAL seconds."
  sleep $CHECK_INTERVAL
done