#!/bin/bash
set -e

curl -X POST "https://api.twilio.com/2010-04-01/Accounts/$TWILIO_ACCOUNT_SID/Messages.json" \
    --data-urlencode "Body=$MESSAGE" \
    --data-urlencode "From=whatsapp:$TWILIO_WHATSAPP_NUMBER" \
    --data-urlencode "To=whatsapp:$TWILIO_NOTIFY_NUMBER" \
    -u "$TWILIO_ACCOUNT_SID:$TWILIO_AUTH_TOKEN"