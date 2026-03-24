#!/usr/bin/env bash

# change to script directory
cd "${0%/*}"

cd ..
./gradlew clean shadowJar

cd text-ui-test

# Run the program and redirect output to ACTUAL.TXT
java -jar $(find ../build/libs/ -mindepth 1 -print -quit) < input.txt > ACTUAL.TXT

# Compare the raw files directly
# -w ignores whitespace differences
diff -w EXPECTED.TXT ACTUAL.TXT

if [ $? -eq 0 ]
then
    echo "Test passed!"
    exit 0
else
    echo "Test failed!"
    exit 1
fi