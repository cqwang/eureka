/*
 * Copyright 2014 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.netflix.eureka.server;

import com.netflix.eureka.registry.datacenter.LocalDataCenterInfo.DataCenterType;
import com.netflix.eureka.transport.EurekaTransports;
import com.netflix.eureka.transport.EurekaTransports.Codec;
import com.netflix.governator.annotations.Configuration;

/**
 * This class contains essential configuration data that are required during Eureka write server
 * bootstrapping. Multiple sources of this data are supported, like command line arguments,
 * property configuration file and archaius.
 *
 * @author Tomasz Bak
 */
public abstract class EurekaBootstrapConfig {

    @Configuration("writeCluster.resolverType")
    private String resolverType = "inline";

    @Configuration("services.registration.port")
    private int registrationPort = EurekaTransports.DEFAULT_REGISTRATION_PORT;

    @Configuration("services.replication.port")
    private int replicationPort = EurekaTransports.DEFAULT_REPLICATION_PORT;

    @Configuration("services.discovery.port")
    private int discoveryServerPort = EurekaTransports.DEFAULT_DISCOVERY_PORT;

    @Configuration("services.shutdown.port")
    private int shutDownPort = 7700;

    @Configuration("dataCenter.type")
    private String dataCenterType = DataCenterType.Basic.name();

    @Configuration("applicationName")
    private String appName = "eurekaWriteCluster";

    @Configuration("vipAddress")
    private String vipAddress = "eurekaWriteCluster";

    @Configuration("writeCluster.servers")
    private String[] writeClusterServers = {"localhost"};

    @Configuration("writeCluster.domainName")
    private String writeClusterDomainName;

    // For property injection
    protected EurekaBootstrapConfig() {
    }

    protected EurekaBootstrapConfig(DataCenterType dataCenterType, String resolverType,
                                    int registrationPort, int replicationPort, int discoveryServerPort, int shutDownPort,
                                    String appName, String vipAddress, String writeClusterDomainName, String[] writeClusterServers) {
        this.resolverType = resolverType;
        this.registrationPort = registrationPort;
        this.replicationPort = replicationPort;
        this.discoveryServerPort = discoveryServerPort;
        this.shutDownPort = shutDownPort;
        this.dataCenterType = dataCenterType.name();
        this.appName = appName;
        this.vipAddress = vipAddress;
        this.writeClusterDomainName = writeClusterDomainName;
        this.writeClusterServers = writeClusterServers;
    }

    public DataCenterType getDataCenterType() {
        return DataCenterType.valueOf(dataCenterType);
    }

    public String getResolverType() {
        return resolverType;
    }

    public int getRegistrationPort() {
        return registrationPort;
    }

    public int getReplicationPort() {
        return replicationPort;
    }

    public int getDiscoveryPort() {
        return discoveryServerPort;
    }

    public int getShutDownPort() {
        return shutDownPort;
    }

    public String getAppName() {
        return appName;
    }

    public String getVipAddress() {
        return vipAddress;
    }

    public String[] getWriteClusterServers() {
        return writeClusterServers;
    }

    public String getWriteClusterDomainName() {
        return writeClusterDomainName;
    }

    public Codec getCodec() {
        return Codec.Json;
    }

    public abstract static class EurekaBootstrapConfigBuilder<C extends EurekaBootstrapConfig, B extends EurekaBootstrapConfigBuilder<C, B>> {
        protected boolean helpOption;
        protected DataCenterType dataCenterType;
        protected String resolverType;
        protected int writeServerPort;
        protected int replicationPort;
        protected int readServerPort;
        protected int shutDownPort;
        protected String appName;
        protected String vipAddress;
        protected String[] writeClusterServers;
        protected String writeClusterDomainName;

        protected EurekaBootstrapConfigBuilder() {
        }

        public B withHelpOption(boolean helpOption) {
            this.helpOption = helpOption;
            return self();
        }

        public B withResolverType(String resolverType) {
            this.resolverType = resolverType;
            return self();
        }

        public B withWriteServerPort(int writeServerPort) {
            this.writeServerPort = writeServerPort;
            return self();
        }

        public B withReplicationPort(int replicationPort) {
            this.replicationPort = replicationPort;
            return self();
        }

        public B withReadServerPort(int readServerPort) {
            this.readServerPort = readServerPort;
            return self();
        }

        public B withShutDownPort(int shutDownPort) {
            this.shutDownPort = shutDownPort;
            return self();
        }

        public B withDataCenterType(DataCenterType dataCenterType) {
            this.dataCenterType = dataCenterType;
            return self();
        }

        public B withAppName(String appName) {
            this.appName = appName;
            return self();
        }

        public B withVipAddress(String vipAddress) {
            this.vipAddress = vipAddress;
            return self();
        }

        public B withWriteClusterAddresses(String[] rest) {
            this.writeClusterServers = rest;
            return self();
        }

        public B withWriteClusterDomainName(String writeClusterDomainName) {
            this.writeClusterDomainName = writeClusterDomainName;
            return self();
        }

        public abstract C build();

        @SuppressWarnings("unchecked")
        protected B self() {
            return (B) this;
        }
    }
}
