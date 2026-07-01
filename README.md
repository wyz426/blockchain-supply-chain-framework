# Blockchain Supply-Chain Framework

A research prototype for configurable blockchain-based supply-chain system construction.

## Overview

This repository contains a web-based prototype for configuring and preparing blockchain-based supply-chain systems. The prototype applies Domain-Driven Design (DDD) and a microkernel-inspired modular structure to organize reusable supply-chain capabilities and scenario-specific extensions.

The platform supports the selection and configuration of business modules, then prepares Hyperledger Fabric-oriented artifacts and deployment resources for the configured system.

## Main Functions

* Manage supply-chain applications, organizations, blockchain networks, and system modules.
* Select reusable business modules and configure custom fields.
* Manage channels, chaincode-related configurations, and deployment requests.
* Provide Hyperledger Fabric configuration templates, deployment scripts, and client-side integration support.

## Repository Structure

```text
blockchain-supply-chain-framework/
├── frontend/                 # Vue frontend
├── backend/                  # Spring Boot backend
│   ├── sql/                  # Database schema scripts
│   └── src/main/resources/   # Fabric templates and deployment resources
├── README.md
└── .gitignore
```

## Technology Stack

* Frontend: Vue, Element UI, Axios
* Backend: Spring Boot, Java, MyBatis-Plus
* Database: MySQL
* Blockchain platform: Hyperledger Fabric
* Supporting resources: Fabric configuration templates and deployment scripts

## Prerequisites

Before running the prototype, prepare:

* Java and Maven
* Node.js and npm
* MySQL
* A locally accessible Hyperledger Fabric environment for blockchain-related deployment operations

## Local Configuration

Before starting the backend, configure the following items in:

```text
backend/src/main/resources/application.yml
```

* MySQL connection URL, username, and password
* Local path for Fabric network resources
* Optional cloud-storage settings, when applicable

The frontend API base path is configured through the environment files under:

```text
frontend/.env.development
frontend/.env.production
```

Adjust the frontend routing or reverse-proxy configuration so that frontend requests can reach the backend service.

## Database Initialization

Database schema scripts are located in:

```text
backend/sql/
```

Create a local MySQL database and import the required schema scripts before starting the backend service.
