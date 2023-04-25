############################################################################
#                                                                          #
# Copyright 2019 Vincenzo De Notaris                                       #
#                                                                          #
# Licensed under the Apache License, Version 2.0 (the "License");          #
# you may not use this file except in compliance with the License.         #
# You may obtain a copy of the License at                                  #
#                                                                          #
#     http://www.apache.org/licenses/LICENSE-2.0                           #
#                                                                          #
# Unless required by applicable law or agreed to in writing, software      #
# distributed under the License is distributed on an "AS IS" BASIS,        #
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. #
# See the License for the specific language governing permissions and      #
# limitations under the License.                                           #
#                                                                          #
############################################################################

# Use Maven to pack a standalone executable fat-JAR file.
FROM maven:3.8.3-openjdk-17 AS build


COPY . /usr/src/app

# Setup working directory
WORKDIR /usr/src/app

# Speed up Maven JVM a bit
ENV MAVEN_OPTS="-XX:+TieredCompilation -XX:TieredStopAtLevel=1"


# Compile the code, run unit tests and pack the fat-JAR file
RUN mvn clean package

############################################################################

# Base Alpine Linux based image with OpenJDK JRE only
FROM openjdk:17-alpine

# Create a group and user
RUN addgroup -S appgroup && adduser -S appuser -G appgroup

# All future commands should run as the appuser user
USER appuser

# Setup working directory
WORKDIR /home/appuser

# Get the packed fat-JAR
COPY --from=build /usr/src/app/backend.jar /home/appuser/app/app.jar

# Make port 8991 available to the world outside this container
EXPOSE 8991

# Setup application entry point
ENTRYPOINT ["java","-jar","/home/appuser/app/app.jar", "--spring.config.additional-location=file:/configuration/"]

############################################################################
