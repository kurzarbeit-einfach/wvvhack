#!/bin/bash

cd /git
git add *
git commit -am "Auto-Commit"
git pull
git push

rsync -av --delete --exclude '.git' --exclude 'config' --exclude 'db' --exclude 'deployment' --exclude 'httpdocs/logs' /git/ /github

cd /github
git add *
git commit -am "Auto-Commit"
git pull
git push

