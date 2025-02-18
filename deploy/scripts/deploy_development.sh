#!/bin/bash
set -e

echo "Triggering deployment for the Railway development environment..."

RAILWAY_PROJECT_ID="5824a4b7-2bd9-45dd-96a5-188788ca1720"
RAILWAY_ENVIRONMENT_ID="7d111a97-b938-4184-a351-a8237e553c98"
RAILWAY_SERVICE_ID="cb31c136-511d-4fb7-a7da-5b0e278e1677"

# Create JSON payload correctly
GRAPHQL_QUERY=$(jq -n \
  --arg projectId "$RAILWAY_PROJECT_ID" \
  --arg environmentId "$RAILWAY_ENVIRONMENT_ID" \
  --arg serviceId "$RAILWAY_SERVICE_ID" \
  '{
    query: "mutation DeployService($projectId: String!, $environmentId: String!, $serviceId: String!) { deploymentCreate(input: { projectId: $projectId, environmentId: $environmentId, serviceId: $serviceId }) { id } }",
    variables: {
      projectId: $projectId,
      environmentId: $environmentId,
      serviceId: $serviceId
    }
  }')

# Send deployment request
RESPONSE=$(curl -s -X POST "https://backboard.railway.app/graphql/v2" \
     -H "Content-Type: application/json" \
     -H "Authorization: Bearer $RAILWAY_API_TOKEN" \
     -d "$GRAPHQL_QUERY")

# Log response
echo "Response from Railway: $RESPONSE"

# Check if deployment succeeded
if echo "$RESPONSE" | grep -q '"errors"'; then
  echo "Error: Deployment failed!"
  exit 1
fi

echo "Deployment request sent successfully."
