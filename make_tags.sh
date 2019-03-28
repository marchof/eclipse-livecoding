#!/bin/bash

# delete all "demo*" remote and local tags
git push origin --delete $(git tag -l | grep "demo")
git tag -d $(git tag -l | grep "demo")

# tag all commits of branch "demo" excepts first one
declare -i t=1
for commit in $(git log --reverse --format=format:%H demo | tail -n +2); do
  name=$(printf "%02d" $t)
  git tag demo/$name $commit
  t=$t+1
done

git push --tags
