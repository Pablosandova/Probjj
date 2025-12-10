#!/usr/bin/env bash
# exit on error
set -o errexit

# Install Java 17 if not present
if [ ! -d "/opt/render/project/.jdks/openjdk17" ]; then
  curl -s "https://get.sdkman.io" | bash
  source "$HOME/.sdkman/bin/sdkman-init.sh"
  sdk install java 17.0.8-tem
fi

# Build the project
./mvnw clean install -DskipTests
