package io.hashimati.microcli.domains.spring;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;




@Data
public class SpringDependencies {
    private String bootVersion;
    private Dependencies dependencies;
    private Repositories repositories;
    private Boms boms;
    public String getBootVersion() {
        return bootVersion;
    }
    public void setBootVersion(String bootVersion) {
        this.bootVersion = bootVersion;
    }
    public Dependencies getDependencies() {
        return dependencies;
    }
    public void setDependencies(Dependencies dependencies) {
        this.dependencies = dependencies;
    }
    public Repositories getRepositories() {
        return repositories;
    }
    public void setRepositories(Repositories repositories) {
        this.repositories = repositories;
    }
    public Boms getBoms() {
        return boms;
    }
    public void setBoms(Boms boms) {
        this.boms = boms;
    }

    @Data
    public static class Hsql {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CodecentricSpringBootAdminServer {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class CloudStarterConsulConfig {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class SpringMilestones {
        private String name;
        private String url;
        private Boolean snapshotEnabled;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public Boolean getSnapshotEnabled() {
            return snapshotEnabled;
        }
        public void setSnapshotEnabled(Boolean snapshotEnabled) {
            this.snapshotEnabled = snapshotEnabled;
        }
    }

    @Data public static class CloudStarterZookeeperConfig {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Boms {
        private SpringCloudAzure springCloudAzure;
        private CodecentricSpringBootAdmin codecentricSpringBootAdmin;
        private SpringCloud springCloud;
        private SpringShell_ springShell;
        private Testcontainers_ testcontainers;
        public SpringCloudAzure getSpringCloudAzure() {
            return springCloudAzure;
        }
        public void setSpringCloudAzure(SpringCloudAzure springCloudAzure) {
            this.springCloudAzure = springCloudAzure;
        }
        public CodecentricSpringBootAdmin getCodecentricSpringBootAdmin() {
            return codecentricSpringBootAdmin;
        }
        public void setCodecentricSpringBootAdmin(CodecentricSpringBootAdmin codecentricSpringBootAdmin) {
            this.codecentricSpringBootAdmin = codecentricSpringBootAdmin;
        }
        public SpringCloud getSpringCloud() {
            return springCloud;
        }
        public void setSpringCloud(SpringCloud springCloud) {
            this.springCloud = springCloud;
        }
        public SpringShell_ getSpringShell() {
            return springShell;
        }
        public void setSpringShell(SpringShell_ springShell) {
            this.springShell = springShell;
        }
        public Testcontainers_ getTestcontainers() {
            return testcontainers;
        }
        public void setTestcontainers(Testcontainers_ testcontainers) {
            this.testcontainers = testcontainers;
        }
    }

    @Data public static class Native {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudTask {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Oauth2Client {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class AzureStorage {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class DataMongodb {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataLdap {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudConfigClient {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class CloudStarterConsulDiscovery {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class DataNeo4j {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataCouchbase {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class SpringShell_ {
        private String groupId;
        private String artifactId;
        private String version;
        private List<String> repositories = new ArrayList<String>();
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public List<String> getRepositories() {
            return repositories;
        }
        public void setRepositories(List<String> repositories) {
            this.repositories = repositories;
        }
    }

    @Data public static class CodecentricSpringBootAdminClient {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Prometheus {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudBus {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Datadog {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Mariadb {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudConfigServer {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class ConfigurationProcessor {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class AzureActiveDirectory {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class CloudEureka {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        private String repository;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
        public String getRepository() {
            return repository;
        }
        public void setRepository(String repository) {
            this.repository = repository;
        }
    }

    @Data public static class SpringShell {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class CloudFunction {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class DataElasticsearch {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Oracle {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Validation {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DistributedTracing {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class H2 {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class WebServices {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Lombok {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Repositories {
        private SpringMilestones springMilestones;
        private NetflixCandidates netflixCandidates;
        public SpringMilestones getSpringMilestones() {
            return springMilestones;
        }
        public void setSpringMilestones(SpringMilestones springMilestones) {
            this.springMilestones = springMilestones;
        }
        public NetflixCandidates getNetflixCandidates() {
            return netflixCandidates;
        }
        public void setNetflixCandidates(NetflixCandidates netflixCandidates) {
            this.netflixCandidates = netflixCandidates;
        }
    }

    @Data public static class DataRedisReactive {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataJdbc {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Freemarker {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Mail {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Jooq {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Devtools {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudFeign {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Mybatis {
        private String groupId;
        private String artifactId;
        private String version;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class AzureSupport {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Security {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Batch {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataCassandraReactive {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudContractStubRunner {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Zipkin {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class SpringCloudAzure {
        private String groupId;
        private String artifactId;
        private String version;
        private List<Object> repositories = new ArrayList<Object>();
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public List<Object> getRepositories() {
            return repositories;
        }
        public void setRepositories(List<Object> repositories) {
            this.repositories = repositories;
        }
    }

    @Data public static class Webflux {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Wavefront {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Websocket {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Mysql {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class KafkaStreams {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Influx {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Kafka {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Flyway {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudStarterVaultConfig {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Graphql {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Artemis {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Session {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Oauth2ResourceServer {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataRestExplorer {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataR2dbc {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Jersey {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudStream {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Web {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Graphite {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataJpa {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CodecentricSpringBootAdmin {
        private String groupId;
        private String artifactId;
        private String version;
        private List<Object> repositories = new ArrayList<Object>();
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public List<Object> getRepositories() {
            return repositories;
        }
        public void setRepositories(List<Object> repositories) {
            this.repositories = repositories;
        }
    }

    @Data public static class Testcontainers_ {
        private String groupId;
        private String artifactId;
        private String version;
        private List<Object> repositories = new ArrayList<Object>();
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public List<Object> getRepositories() {
            return repositories;
        }
        public void setRepositories(List<Object> repositories) {
            this.repositories = repositories;
        }
    }

    @Data public static class Sqlserver {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class NetflixCandidates {
        private String name;
        private String url;
        private Boolean snapshotEnabled;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public String getUrl() {
            return url;
        }
        public void setUrl(String url) {
            this.url = url;
        }
        public Boolean getSnapshotEnabled() {
            return snapshotEnabled;
        }
        public void setSnapshotEnabled(Boolean snapshotEnabled) {
            this.snapshotEnabled = snapshotEnabled;
        }
    }

    @Data public static class DataCouchbaseReactive {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudStarter {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Actuator {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class UnboundidLdap {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudResilience4j {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Integration {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Picocli {
        private String groupId;
        private String artifactId;
        private String version;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudContractVerifier {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Dependencies {
        private Actuator actuator;
        private DataRedisReactive dataRedisReactive;
        private CloudContractStubRunner cloudContractStubRunner;
        private Validation validation;
        private AzureSupport azureSupport;
        private CloudStarterZookeeperConfig cloudStarterZookeeperConfig;
        private Cache cache;
        private SpringShell springShell;
        private Zipkin zipkin;
        private DataRestExplorer dataRestExplorer;
        private CodecentricSpringBootAdminClient codecentricSpringBootAdminClient;
        private AzureKeyvault azureKeyvault;
        private DataCassandra dataCassandra;
        private CloudStarterConsulDiscovery cloudStarterConsulDiscovery;
        private Kafka kafka;
        private AzureActiveDirectory azureActiveDirectory;
        private Integration integration;
        private CloudConfigServer cloudConfigServer;
        private Freemarker freemarker;
        private Native _native;
        private Web web;
        private DataCassandraReactive dataCassandraReactive;
        private Picocli picocli;
        private Hsql hsql;
        private Datadog datadog;
        private GroovyTemplates groovyTemplates;
        private DataCouchbaseReactive dataCouchbaseReactive;
        private Devtools devtools;
        private Graphql graphql;
        private Oauth2Client oauth2Client;
        private Testcontainers testcontainers;
        private CloudStarter cloudStarter;
        private Amqp amqp;
        private Rsocket rsocket;
        private CloudTask cloudTask;
        private CloudBus cloudBus;
        private KafkaStreams kafkaStreams;
        private CloudResilience4j cloudResilience4j;
        private Db2 db2;
        private UnboundidLdap unboundidLdap;
        private DataLdap dataLdap;
        private DataNeo4j dataNeo4j;
        private DataRest dataRest;
        private NewRelic newRelic;
        private Mail mail;
        private DistributedTracing distributedTracing;
        private CloudStream cloudStream;
        private Graphite graphite;
        private Liquibase liquibase;
        private Websocket websocket;
        private Derby derby;
        private Mysql mysql;
        private DataCouchbase dataCouchbase;
        private CloudStarterConsulConfig cloudStarterConsulConfig;
        private WebServices webServices;
        private CloudLoadbalancer cloudLoadbalancer;
        private Oracle oracle;
        private ConfigurationProcessor configurationProcessor;
        private Flyway flyway;
        private Batch batch;
        private DataJpa dataJpa;
        private Thymeleaf thymeleaf;
        private CloudEureka cloudEureka;
        private DataJdbc dataJdbc;
        private Lombok lombok;
        private CloudStarterVaultConfig cloudStarterVaultConfig;
        private CodecentricSpringBootAdminServer codecentricSpringBootAdminServer;
        private DataRedis dataRedis;
        private Wavefront wavefront;
        private CloudStarterZookeeperDiscovery cloudStarterZookeeperDiscovery;
        private Session session;
        private Mybatis mybatis;
        private Webflux webflux;
        private DataR2dbc dataR2dbc;
        private DataMongodb dataMongodb;
        private Jdbc jdbc;
        private H2 h2;
        private CloudContractVerifier cloudContractVerifier;
        private Mustache mustache;
        private CloudGateway cloudGateway;
        private DataMongodbReactive dataMongodbReactive;
        private CloudFeign cloudFeign;
        private Security security;
        private Sqlserver sqlserver;
        private Postgresql postgresql;
        private Jersey jersey;
        private Jooq jooq;
        private Hateoas hateoas;
        private Prometheus prometheus;
        private Oauth2ResourceServer oauth2ResourceServer;
        private DataElasticsearch dataElasticsearch;
        private Artemis artemis;
        private Influx influx;
        private Restdocs restdocs;
        private Quartz quartz;
        private CloudFunction cloudFunction;
        private Mariadb mariadb;
        private AzureStorage azureStorage;
        private CloudEurekaServer cloudEurekaServer;
        private CloudConfigClient cloudConfigClient;
        public Actuator getActuator() {
            return actuator;
        }
        public void setActuator(Actuator actuator) {
            this.actuator = actuator;
        }
        public DataRedisReactive getDataRedisReactive() {
            return dataRedisReactive;
        }
        public void setDataRedisReactive(DataRedisReactive dataRedisReactive) {
            this.dataRedisReactive = dataRedisReactive;
        }
        public CloudContractStubRunner getCloudContractStubRunner() {
            return cloudContractStubRunner;
        }
        public void setCloudContractStubRunner(CloudContractStubRunner cloudContractStubRunner) {
            this.cloudContractStubRunner = cloudContractStubRunner;
        }
        public Validation getValidation() {
            return validation;
        }
        public void setValidation(Validation validation) {
            this.validation = validation;
        }
        public AzureSupport getAzureSupport() {
            return azureSupport;
        }
        public void setAzureSupport(AzureSupport azureSupport) {
            this.azureSupport = azureSupport;
        }
        public CloudStarterZookeeperConfig getCloudStarterZookeeperConfig() {
            return cloudStarterZookeeperConfig;
        }
        public void setCloudStarterZookeeperConfig(CloudStarterZookeeperConfig cloudStarterZookeeperConfig) {
            this.cloudStarterZookeeperConfig = cloudStarterZookeeperConfig;
        }
        public Cache getCache() {
            return cache;
        }
        public void setCache(Cache cache) {
            this.cache = cache;
        }
        public SpringShell getSpringShell() {
            return springShell;
        }
        public void setSpringShell(SpringShell springShell) {
            this.springShell = springShell;
        }
        public Zipkin getZipkin() {
            return zipkin;
        }
        public void setZipkin(Zipkin zipkin) {
            this.zipkin = zipkin;
        }
        public DataRestExplorer getDataRestExplorer() {
            return dataRestExplorer;
        }
        public void setDataRestExplorer(DataRestExplorer dataRestExplorer) {
            this.dataRestExplorer = dataRestExplorer;
        }
        public CodecentricSpringBootAdminClient getCodecentricSpringBootAdminClient() {
            return codecentricSpringBootAdminClient;
        }
        public void setCodecentricSpringBootAdminClient(CodecentricSpringBootAdminClient codecentricSpringBootAdminClient) {
            this.codecentricSpringBootAdminClient = codecentricSpringBootAdminClient;
        }
        public AzureKeyvault getAzureKeyvault() {
            return azureKeyvault;
        }
        public void setAzureKeyvault(AzureKeyvault azureKeyvault) {
            this.azureKeyvault = azureKeyvault;
        }
        public DataCassandra getDataCassandra() {
            return dataCassandra;
        }
        public void setDataCassandra(DataCassandra dataCassandra) {
            this.dataCassandra = dataCassandra;
        }
        public CloudStarterConsulDiscovery getCloudStarterConsulDiscovery() {
            return cloudStarterConsulDiscovery;
        }
        public void setCloudStarterConsulDiscovery(CloudStarterConsulDiscovery cloudStarterConsulDiscovery) {
            this.cloudStarterConsulDiscovery = cloudStarterConsulDiscovery;
        }
        public Kafka getKafka() {
            return kafka;
        }
        public void setKafka(Kafka kafka) {
            this.kafka = kafka;
        }
        public AzureActiveDirectory getAzureActiveDirectory() {
            return azureActiveDirectory;
        }
        public void setAzureActiveDirectory(AzureActiveDirectory azureActiveDirectory) {
            this.azureActiveDirectory = azureActiveDirectory;
        }
        public Integration getIntegration() {
            return integration;
        }
        public void setIntegration(Integration integration) {
            this.integration = integration;
        }
        public CloudConfigServer getCloudConfigServer() {
            return cloudConfigServer;
        }
        public void setCloudConfigServer(CloudConfigServer cloudConfigServer) {
            this.cloudConfigServer = cloudConfigServer;
        }
        public Freemarker getFreemarker() {
            return freemarker;
        }
        public void setFreemarker(Freemarker freemarker) {
            this.freemarker = freemarker;
        }
        public Native getNative() {
            return _native;
        }
        public void setNative(Native _native) {
            this._native = _native;
        }
        public Web getWeb() {
            return web;
        }
        public void setWeb(Web web) {
            this.web = web;
        }
        public DataCassandraReactive getDataCassandraReactive() {
            return dataCassandraReactive;
        }
        public void setDataCassandraReactive(DataCassandraReactive dataCassandraReactive) {
            this.dataCassandraReactive = dataCassandraReactive;
        }
        public Picocli getPicocli() {
            return picocli;
        }
        public void setPicocli(Picocli picocli) {
            this.picocli = picocli;
        }
        public Hsql getHsql() {
            return hsql;
        }
        public void setHsql(Hsql hsql) {
            this.hsql = hsql;
        }
        public Datadog getDatadog() {
            return datadog;
        }
        public void setDatadog(Datadog datadog) {
            this.datadog = datadog;
        }
        public GroovyTemplates getGroovyTemplates() {
            return groovyTemplates;
        }
        public void setGroovyTemplates(GroovyTemplates groovyTemplates) {
            this.groovyTemplates = groovyTemplates;
        }
        public DataCouchbaseReactive getDataCouchbaseReactive() {
            return dataCouchbaseReactive;
        }
        public void setDataCouchbaseReactive(DataCouchbaseReactive dataCouchbaseReactive) {
            this.dataCouchbaseReactive = dataCouchbaseReactive;
        }
        public Devtools getDevtools() {
            return devtools;
        }
        public void setDevtools(Devtools devtools) {
            this.devtools = devtools;
        }
        public Graphql getGraphql() {
            return graphql;
        }
        public void setGraphql(Graphql graphql) {
            this.graphql = graphql;
        }
        public Oauth2Client getOauth2Client() {
            return oauth2Client;
        }
        public void setOauth2Client(Oauth2Client oauth2Client) {
            this.oauth2Client = oauth2Client;
        }
        public Testcontainers getTestcontainers() {
            return testcontainers;
        }
        public void setTestcontainers(Testcontainers testcontainers) {
            this.testcontainers = testcontainers;
        }
        public CloudStarter getCloudStarter() {
            return cloudStarter;
        }
        public void setCloudStarter(CloudStarter cloudStarter) {
            this.cloudStarter = cloudStarter;
        }
        public Amqp getAmqp() {
            return amqp;
        }
        public void setAmqp(Amqp amqp) {
            this.amqp = amqp;
        }
        public Rsocket getRsocket() {
            return rsocket;
        }
        public void setRsocket(Rsocket rsocket) {
            this.rsocket = rsocket;
        }
        public CloudTask getCloudTask() {
            return cloudTask;
        }
        public void setCloudTask(CloudTask cloudTask) {
            this.cloudTask = cloudTask;
        }
        public CloudBus getCloudBus() {
            return cloudBus;
        }
        public void setCloudBus(CloudBus cloudBus) {
            this.cloudBus = cloudBus;
        }
        public KafkaStreams getKafkaStreams() {
            return kafkaStreams;
        }
        public void setKafkaStreams(KafkaStreams kafkaStreams) {
            this.kafkaStreams = kafkaStreams;
        }
        public CloudResilience4j getCloudResilience4j() {
            return cloudResilience4j;
        }
        public void setCloudResilience4j(CloudResilience4j cloudResilience4j) {
            this.cloudResilience4j = cloudResilience4j;
        }
        public Db2 getDb2() {
            return db2;
        }
        public void setDb2(Db2 db2) {
            this.db2 = db2;
        }
        public UnboundidLdap getUnboundidLdap() {
            return unboundidLdap;
        }
        public void setUnboundidLdap(UnboundidLdap unboundidLdap) {
            this.unboundidLdap = unboundidLdap;
        }
        public DataLdap getDataLdap() {
            return dataLdap;
        }
        public void setDataLdap(DataLdap dataLdap) {
            this.dataLdap = dataLdap;
        }
        public DataNeo4j getDataNeo4j() {
            return dataNeo4j;
        }
        public void setDataNeo4j(DataNeo4j dataNeo4j) {
            this.dataNeo4j = dataNeo4j;
        }
        public DataRest getDataRest() {
            return dataRest;
        }
        public void setDataRest(DataRest dataRest) {
            this.dataRest = dataRest;
        }
        public NewRelic getNewRelic() {
            return newRelic;
        }
        public void setNewRelic(NewRelic newRelic) {
            this.newRelic = newRelic;
        }
        public Mail getMail() {
            return mail;
        }
        public void setMail(Mail mail) {
            this.mail = mail;
        }
        public DistributedTracing getDistributedTracing() {
            return distributedTracing;
        }
        public void setDistributedTracing(DistributedTracing distributedTracing) {
            this.distributedTracing = distributedTracing;
        }
        public CloudStream getCloudStream() {
            return cloudStream;
        }
        public void setCloudStream(CloudStream cloudStream) {
            this.cloudStream = cloudStream;
        }
        public Graphite getGraphite() {
            return graphite;
        }
        public void setGraphite(Graphite graphite) {
            this.graphite = graphite;
        }
        public Liquibase getLiquibase() {
            return liquibase;
        }
        public void setLiquibase(Liquibase liquibase) {
            this.liquibase = liquibase;
        }
        public Websocket getWebsocket() {
            return websocket;
        }
        public void setWebsocket(Websocket websocket) {
            this.websocket = websocket;
        }
        public Derby getDerby() {
            return derby;
        }
        public void setDerby(Derby derby) {
            this.derby = derby;
        }
        public Mysql getMysql() {
            return mysql;
        }
        public void setMysql(Mysql mysql) {
            this.mysql = mysql;
        }
        public DataCouchbase getDataCouchbase() {
            return dataCouchbase;
        }
        public void setDataCouchbase(DataCouchbase dataCouchbase) {
            this.dataCouchbase = dataCouchbase;
        }
        public CloudStarterConsulConfig getCloudStarterConsulConfig() {
            return cloudStarterConsulConfig;
        }
        public void setCloudStarterConsulConfig(CloudStarterConsulConfig cloudStarterConsulConfig) {
            this.cloudStarterConsulConfig = cloudStarterConsulConfig;
        }
        public WebServices getWebServices() {
            return webServices;
        }
        public void setWebServices(WebServices webServices) {
            this.webServices = webServices;
        }
        public CloudLoadbalancer getCloudLoadbalancer() {
            return cloudLoadbalancer;
        }
        public void setCloudLoadbalancer(CloudLoadbalancer cloudLoadbalancer) {
            this.cloudLoadbalancer = cloudLoadbalancer;
        }
        public Oracle getOracle() {
            return oracle;
        }
        public void setOracle(Oracle oracle) {
            this.oracle = oracle;
        }
        public ConfigurationProcessor getConfigurationProcessor() {
            return configurationProcessor;
        }
        public void setConfigurationProcessor(ConfigurationProcessor configurationProcessor) {
            this.configurationProcessor = configurationProcessor;
        }
        public Flyway getFlyway() {
            return flyway;
        }
        public void setFlyway(Flyway flyway) {
            this.flyway = flyway;
        }
        public Batch getBatch() {
            return batch;
        }
        public void setBatch(Batch batch) {
            this.batch = batch;
        }
        public DataJpa getDataJpa() {
            return dataJpa;
        }
        public void setDataJpa(DataJpa dataJpa) {
            this.dataJpa = dataJpa;
        }
        public Thymeleaf getThymeleaf() {
            return thymeleaf;
        }
        public void setThymeleaf(Thymeleaf thymeleaf) {
            this.thymeleaf = thymeleaf;
        }
        public CloudEureka getCloudEureka() {
            return cloudEureka;
        }
        public void setCloudEureka(CloudEureka cloudEureka) {
            this.cloudEureka = cloudEureka;
        }
        public DataJdbc getDataJdbc() {
            return dataJdbc;
        }
        public void setDataJdbc(DataJdbc dataJdbc) {
            this.dataJdbc = dataJdbc;
        }
        public Lombok getLombok() {
            return lombok;
        }
        public void setLombok(Lombok lombok) {
            this.lombok = lombok;
        }
        public CloudStarterVaultConfig getCloudStarterVaultConfig() {
            return cloudStarterVaultConfig;
        }
        public void setCloudStarterVaultConfig(CloudStarterVaultConfig cloudStarterVaultConfig) {
            this.cloudStarterVaultConfig = cloudStarterVaultConfig;
        }
        public CodecentricSpringBootAdminServer getCodecentricSpringBootAdminServer() {
            return codecentricSpringBootAdminServer;
        }
        public void setCodecentricSpringBootAdminServer(CodecentricSpringBootAdminServer codecentricSpringBootAdminServer) {
            this.codecentricSpringBootAdminServer = codecentricSpringBootAdminServer;
        }
        public DataRedis getDataRedis() {
            return dataRedis;
        }
        public void setDataRedis(DataRedis dataRedis) {
            this.dataRedis = dataRedis;
        }
        public Wavefront getWavefront() {
            return wavefront;
        }
        public void setWavefront(Wavefront wavefront) {
            this.wavefront = wavefront;
        }
        public CloudStarterZookeeperDiscovery getCloudStarterZookeeperDiscovery() {
            return cloudStarterZookeeperDiscovery;
        }
        public void setCloudStarterZookeeperDiscovery(CloudStarterZookeeperDiscovery cloudStarterZookeeperDiscovery) {
            this.cloudStarterZookeeperDiscovery = cloudStarterZookeeperDiscovery;
        }
        public Session getSession() {
            return session;
        }
        public void setSession(Session session) {
            this.session = session;
        }
        public Mybatis getMybatis() {
            return mybatis;
        }
        public void setMybatis(Mybatis mybatis) {
            this.mybatis = mybatis;
        }
        public Webflux getWebflux() {
            return webflux;
        }
        public void setWebflux(Webflux webflux) {
            this.webflux = webflux;
        }
        public DataR2dbc getDataR2dbc() {
            return dataR2dbc;
        }
        public void setDataR2dbc(DataR2dbc dataR2dbc) {
            this.dataR2dbc = dataR2dbc;
        }
        public DataMongodb getDataMongodb() {
            return dataMongodb;
        }
        public void setDataMongodb(DataMongodb dataMongodb) {
            this.dataMongodb = dataMongodb;
        }
        public Jdbc getJdbc() {
            return jdbc;
        }
        public void setJdbc(Jdbc jdbc) {
            this.jdbc = jdbc;
        }
        public H2 getH2() {
            return h2;
        }
        public void setH2(H2 h2) {
            this.h2 = h2;
        }
        public CloudContractVerifier getCloudContractVerifier() {
            return cloudContractVerifier;
        }
        public void setCloudContractVerifier(CloudContractVerifier cloudContractVerifier) {
            this.cloudContractVerifier = cloudContractVerifier;
        }
        public Mustache getMustache() {
            return mustache;
        }
        public void setMustache(Mustache mustache) {
            this.mustache = mustache;
        }
        public CloudGateway getCloudGateway() {
            return cloudGateway;
        }
        public void setCloudGateway(CloudGateway cloudGateway) {
            this.cloudGateway = cloudGateway;
        }
        public DataMongodbReactive getDataMongodbReactive() {
            return dataMongodbReactive;
        }
        public void setDataMongodbReactive(DataMongodbReactive dataMongodbReactive) {
            this.dataMongodbReactive = dataMongodbReactive;
        }
        public CloudFeign getCloudFeign() {
            return cloudFeign;
        }
        public void setCloudFeign(CloudFeign cloudFeign) {
            this.cloudFeign = cloudFeign;
        }
        public Security getSecurity() {
            return security;
        }
        public void setSecurity(Security security) {
            this.security = security;
        }
        public Sqlserver getSqlserver() {
            return sqlserver;
        }
        public void setSqlserver(Sqlserver sqlserver) {
            this.sqlserver = sqlserver;
        }
        public Postgresql getPostgresql() {
            return postgresql;
        }
        public void setPostgresql(Postgresql postgresql) {
            this.postgresql = postgresql;
        }
        public Jersey getJersey() {
            return jersey;
        }
        public void setJersey(Jersey jersey) {
            this.jersey = jersey;
        }
        public Jooq getJooq() {
            return jooq;
        }
        public void setJooq(Jooq jooq) {
            this.jooq = jooq;
        }
        public Hateoas getHateoas() {
            return hateoas;
        }
        public void setHateoas(Hateoas hateoas) {
            this.hateoas = hateoas;
        }
        public Prometheus getPrometheus() {
            return prometheus;
        }
        public void setPrometheus(Prometheus prometheus) {
            this.prometheus = prometheus;
        }
        public Oauth2ResourceServer getOauth2ResourceServer() {
            return oauth2ResourceServer;
        }
        public void setOauth2ResourceServer(Oauth2ResourceServer oauth2ResourceServer) {
            this.oauth2ResourceServer = oauth2ResourceServer;
        }
        public DataElasticsearch getDataElasticsearch() {
            return dataElasticsearch;
        }
        public void setDataElasticsearch(DataElasticsearch dataElasticsearch) {
            this.dataElasticsearch = dataElasticsearch;
        }
        public Artemis getArtemis() {
            return artemis;
        }
        public void setArtemis(Artemis artemis) {
            this.artemis = artemis;
        }
        public Influx getInflux() {
            return influx;
        }
        public void setInflux(Influx influx) {
            this.influx = influx;
        }
        public Restdocs getRestdocs() {
            return restdocs;
        }
        public void setRestdocs(Restdocs restdocs) {
            this.restdocs = restdocs;
        }
        public Quartz getQuartz() {
            return quartz;
        }
        public void setQuartz(Quartz quartz) {
            this.quartz = quartz;
        }
        public CloudFunction getCloudFunction() {
            return cloudFunction;
        }
        public void setCloudFunction(CloudFunction cloudFunction) {
            this.cloudFunction = cloudFunction;
        }
        public Mariadb getMariadb() {
            return mariadb;
        }
        public void setMariadb(Mariadb mariadb) {
            this.mariadb = mariadb;
        }
        public AzureStorage getAzureStorage() {
            return azureStorage;
        }
        public void setAzureStorage(AzureStorage azureStorage) {
            this.azureStorage = azureStorage;
        }
        public CloudEurekaServer getCloudEurekaServer() {
            return cloudEurekaServer;
        }
        public void setCloudEurekaServer(CloudEurekaServer cloudEurekaServer) {
            this.cloudEurekaServer = cloudEurekaServer;
        }
        public CloudConfigClient getCloudConfigClient() {
            return cloudConfigClient;
        }
        public void setCloudConfigClient(CloudConfigClient cloudConfigClient) {
            this.cloudConfigClient = cloudConfigClient;
        }
    }

    @Data public static class SpringCloud {
        private String groupId;
        private String artifactId;
        private String version;
        private List<Object> repositories = new ArrayList<Object>();
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getVersion() {
            return version;
        }
        public void setVersion(String version) {
            this.version = version;
        }
        public List<Object> getRepositories() {
            return repositories;
        }
        public void setRepositories(List<Object> repositories) {
            this.repositories = repositories;
        }
    }

    @Data public static class Postgresql {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudLoadbalancer {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Mustache {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Rsocket {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Jdbc {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class GroovyTemplates {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Thymeleaf {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudGateway {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class AzureKeyvault {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Testcontainers {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Liquibase {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataRedis {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Db2 {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class NewRelic {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudStarterZookeeperDiscovery {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
    }

    @Data public static class Derby {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Amqp {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Restdocs {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Hateoas {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataRest {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataMongodbReactive {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Quartz {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class Cache {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class DataCassandra {
        private String groupId;
        private String artifactId;
        private String scope;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
    }

    @Data public static class CloudEurekaServer {
        private String groupId;
        private String artifactId;
        private String scope;
        private String bom;
        private String repository;
        public String getGroupId() {
            return groupId;
        }
        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }
        public String getArtifactId() {
            return artifactId;
        }
        public void setArtifactId(String artifactId) {
            this.artifactId = artifactId;
        }
        public String getScope() {
            return scope;
        }
        public void setScope(String scope) {
            this.scope = scope;
        }
        public String getBom() {
            return bom;
        }
        public void setBom(String bom) {
            this.bom = bom;
        }
        public String getRepository() {
            return repository;
        }
        public void setRepository(String repository) {
            this.repository = repository;
        }
    }
}


