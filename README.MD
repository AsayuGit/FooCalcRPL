
# FooCalcRPL

A simple command line reverse polish notation calculator


[![GPLv2 License](https://img.shields.io/badge/License-GPL%20v2-blue.svg)](https://opensource.org/licenses/)
## Features

- Complex and Vector support
- Multi-User over the network
- Shared Stack
- Discrete Stack
- Cross platform


## Usage/Examples

#### Syntax
```bash
  java -jar FooCalcRPL.jar [options]
```

#### Exemple
```bash
  EG: java -jar FooCalcRPL.jar -s
```

#### Options
|  Option   |   Alt         | Description                                           |
| :-------- | :------------ | :---------------------------------------------------- |
| `-h`      | `--help`      | Display this guide                                    |
| `-s`      | `--shared`    | Users can connect and share a same stack               |
| `-d`      | `--discrete`  | Users can connect and use their own stack              |
| `-l`      | `--log`       | Enable logging                                        |
| `-r`      | `--replay`    | Replay a previosly logged session (in local sessions) |

#### To start a standalone session:
```bash
  java -jar FooCalcRPL.jar
```

#### To start a remote session with a shared stack:
```bash
  java -jar FooCalcRPL.jar -s
```

#### To start a remote session with discrete stacks:
```bash
  java -jar FooCalcRPL.jar -d
```

#### To enable logging on a standalone session:
```bash
  java -jar FooCalcRPL.jar -l
```

#### To replay a previous standalone session:
```bash
  java -jar FooCalcRPL.jar -r
```

## Dependencies

In order to be able to build stormbreaker the following dependencies are needed:

### For the docker build:
- docker.io

### For the local build:

- A recent version of Java

## Docker Build

To compile & run in a docker container run:

```bash
  cd FooCalcRPL
  make docker-build
  make docker-run
  java -jar FooCalcRPL.jar
```

A shell from within the newly build docker container will open in the current working directory.

- Do note that you may need to run these commands as root depending on your docker setup.


## Local Build

To compile & run locally run

```bash
  cd FooCalcRPL
  make
  java -jar FooCalcRPL.jar
```
## Authors

- [@AsayuGit](https://github.com/AsayuGit)

## FAQ

#### What is the recommended way to build FooCalcRPL ?

The recommended way to build FooCalcRPL would be via the docker build.
This ensure that all the dependencies for the app will be met.

#### What are the supported OS for FooCalcRPL ?

FooCalcRPL is build with Java so it should be able to run on any OS where the Jvm is install.