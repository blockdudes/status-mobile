FROM gitpod/workspace-full-vnc:latest

RUN make run-clojure && make run-metro && make run-android