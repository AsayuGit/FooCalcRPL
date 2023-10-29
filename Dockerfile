# Use the GCC image to have make and gcc by default
FROM openjdk:21-ea
LABEL org.opencontainers.image.authors="Killian RAIMBAUD <killian.rai@gmail.com>"

# Copy the program sources and moved into the build directory
COPY src /build/src
COPY Makefile /build/Makefile
WORKDIR /build

# Build the app
RUN javac -sourcepath src -d out src/FooCalcRPL.java
RUN jar cfe FooCalcRPL.jar FooCalcRPL -C out .

# Make the executable available throughout the container
RUN mv /build/FooCalcRPL.jar /opt/FooCalcRPL.jar

# Remove the sources from the image
RUN rm -rf /build