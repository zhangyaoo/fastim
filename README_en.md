## FastIM
> 🚀 Based on Netty's highly available distributed instant messaging system, it supports long-connected gateway management, single chat, group chat, login log-out, chat history query, offline message storage, message push, heartbeat, distributed unique ID,redpacket, message synchronization, and roaming, supporting the distributed architecture of cluster deployment.

[Chinese中文文档](https://github.com/zhangyaoo/fastim/blob/master/README.md)

## Project Structure
- fastim-logic:logical services such as single chat, group chat, red envelope, offline, and other business logic
- fastim-client:client logic implementation, enabling sending messages, disconnecting,SDK packages, and more
- fastim-gate-tcp:HTTP API gateway implementation for streaming downgrades, version routing,openAPI management, protocol conversion, generalization calls, and more
- fastim-gate-http:A long-connected TCP gateway implementation that enables custom protocols,channel management, heartbeat detection, generalization calls, and more
- fastim-leaf:Distributed ID implementation, zookeeper-based implementation or full memory-based implementation
- fastim-common:Common classes, mainly the storage of entity and tool classes
- fastim-lsb:LSB service,which provides access layer IP and port for load balancing connections
- fastim-das:Data access layer implementation, encapsulating one layer, providing an addition and deletion portal to the data

## Features

* [x] IM architecture design
* [x] Distributed ID design
* [x] IM table structure design
* [ ] The TCP gateway design implementation
* [ ] HTTP gateway design implementation
* [ ] Communication protocol design and protobuf serialize
* [ ] Single chat, group chat
* [ ] The heartbeat is alive
* [ ] Chat history storage and queries
* [ ] The client disconnects and automatically reconnect
* [ ] Sign in, register, and log out
* [ ] Message push, data escalation
* [ ] Provides a client SDK
* [ ] Multi-side synchronization of messages and message roaming
* [ ] The protocol supports message encryption
* [ ] The only device kicked out
* [ ] Files and pictures provide
* [ ] The protocol supports message encryption
* [ ] Group red envelopes
* [ ] Message Recalled
* [ ] Message read and unread

 

## Quick Start

## System Architecture Diagram
![IM Architecture diagram](https://github.com/zhangyaoo/fastim/blob/master/pic/architecture.jpg)

## Contact

- Website:zhangyaoo.github.io

- WeChat:will_zhangyao