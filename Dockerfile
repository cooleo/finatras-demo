FROM abigail/scala:2.11.8

MAINTAINER Abigail <AbigailBuccaneer@users.noreply.github.com>

ENV SBT_VERSION 0.13.11
RUN apt-get update -y && \
    apt-get install -y --no-install-recommends apt-transport-https && \
    apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv 642AC823 && \
    echo "deb https://dl.bintray.com/sbt/debian /" > /etc/apt/sources.list.d/sbt.list && \
    apt-get update -y && apt-get install -y --no-install-recommends "sbt=$SBT_VERSION" && \
    sbt exit && \
    rm /etc/apt/sources.list.d/sbt.list && \
    apt-get purge -y apt-transport-https && \
    apt-get clean -y && \
    rm -rf /var/lib/apt/lists/*

RUN sbt
