#!/bin/bash

# file path 
f='./app/build.gradle'

v=`grep 'versionName' $f`

# get versionName value from app/build.gradle file
appversionname=v$(echo `grep 'versionName' $f` | grep -Eo '[0-9]{1,4}.[0-9]{1,4}.[0-9]{1,4}')
echo "Version number is "$appversionname

# checking the appversionname exist as tag. if exist return the versiontag else return null
VERSIONTAG="$(git tag -l $appversionname)"

# check the VERSIONTAG and appversionname are same then stop script else create tag, push and upload build on hockeyapp.
if [ "$VERSIONTAG" == "$appversionname" ]
then 
  echo "An error occurred. Tag already exist. Please update versionName to create a new tag. Exiting..."

  #finish the script forcly
  exit 1

else
 # create tag
 git tag $appversionname

 # push the tag on remote
 git push origin $appversionname

 # upload the build on hockey app
 fastlane hockey_build
 echo "Successfully uploaded on hockey app."

fi

