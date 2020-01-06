package com.edmunds.tools.databricks.maven;

import com.edmunds.rest.databricks.DTO.UpsertClusterDTO;
import com.edmunds.tools.databricks.maven.util.EnvironmentDTOSupplier;
import com.edmunds.tools.databricks.maven.util.SettingsInitializer;
import com.edmunds.tools.databricks.maven.util.SettingsUtils;
import java.util.Collections;
import org.apache.maven.plugin.MojoExecutionException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Tests for @{@link BaseDatabricksUpsertClusterMojoSettingsInitializer}.
 */
public class BaseDatabricksUpsertClusterMojoSettingsInitializerTest extends DatabricksMavenPluginTestHarness {

    private SettingsUtils<UpsertClusterDTO> settingsUtils;
    private EnvironmentDTOSupplier environmentDTOSupplier;
    private SettingsInitializer<UpsertClusterDTO> settingsInitializer;

    @BeforeClass
    public void initClass() throws Exception {
        super.setUp();
        UpsertClusterMojo underTest = getNoOverridesMojo("upsert-cluster");
        settingsUtils = underTest.getSettingsUtils();
        environmentDTOSupplier = underTest.getEnvironmentDTOSupplier();
        settingsInitializer = underTest.getSettingsInitializer();
    }

    @Test
    public void testFillInDefaults() throws Exception {
        UpsertClusterDTO defaultSettingsDTO = settingsUtils.defaultSettingsDTO();
        UpsertClusterDTO targetDTO = new UpsertClusterDTO();

        settingsInitializer.fillInDefaults(targetDTO, defaultSettingsDTO, environmentDTOSupplier.get());

        assertEquals(targetDTO.getClusterName(), "unit-test-group/unit-test-artifact");

        assertEquals(targetDTO.getNumWorkers(), defaultSettingsDTO.getNumWorkers());
        assertEquals(targetDTO.getArtifactPaths(), Collections.emptyList());
        assertEquals(targetDTO.getAutoTerminationMinutes(), defaultSettingsDTO.getAutoTerminationMinutes());
        assertEquals(targetDTO.getAwsAttributes(), defaultSettingsDTO.getAwsAttributes());
        assertNull(targetDTO.getClusterLogConf());
        assertNull(targetDTO.getCustomTags());
        assertEquals(targetDTO.getDriverNodeTypeId(), defaultSettingsDTO.getDriverNodeTypeId());
        assertEquals(targetDTO.getNodeTypeId(), defaultSettingsDTO.getNodeTypeId());
        assertEquals(targetDTO.getSparkConf(), defaultSettingsDTO.getSparkConf());
        assertEquals(targetDTO.getSparkEnvVars(), defaultSettingsDTO.getSparkEnvVars());
        assertEquals(targetDTO.getSparkVersion(), defaultSettingsDTO.getSparkVersion());
        assertNull(targetDTO.getSshPublicKeys());
    }

    @Test(expectedExceptions = MojoExecutionException.class,
        expectedExceptionsMessageRegExp = "REQUIRED FIELD \\[cluster_name\\] was empty. VALIDATION FAILED.")
    public void testValidate_whenNoClusterName_exception() throws Exception {
        UpsertClusterDTO targetDTO = new UpsertClusterDTO();

        settingsInitializer.validate(targetDTO, environmentDTOSupplier.get());
    }

    @Test
    public void testValidate_whenClusterNameFilled_noException() throws Exception {
        UpsertClusterDTO targetDTO = new UpsertClusterDTO();
        targetDTO.setClusterName("my-cluster-name");

        settingsInitializer.validate(targetDTO, environmentDTOSupplier.get());
    }

}