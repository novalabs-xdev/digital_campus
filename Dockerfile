FROM ubuntu:latest
LABEL authors="nelsam"

ENTRYPOINT ["top", "-b"]