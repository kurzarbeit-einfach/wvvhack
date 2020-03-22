#!/bin/bash

rsync -avz --exclude '.git' /repos/udo-pdf/ /git/pdf
rsync -avz --exclude '.git' --exclude 'config' --exclude 'db' --exclude 'deployment' --exclude 'httpdocs/logs' /git/ /github

cd /git
git add *
git commit -am "Auto-Commit"
git push

cd /github
git add *
git commit -am "Auto-Commit"
git push

