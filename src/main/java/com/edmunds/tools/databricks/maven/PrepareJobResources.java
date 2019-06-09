/*
 *    Copyright 2018 Edmunds.com, Inc.
 *
 *        Licensed under the Apache License, Version 2.0 (the "License");
 *        you may not use this file except in compliance with the License.
 *        You may obtain a copy of the License at
 *
 *            http://www.apache.org/licenses/LICENSE-2.0
 *
 *        Unless required by applicable law or agreed to in writing, software
 *        distributed under the License is distributed on an "AS IS" BASIS,
 *        WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *        See the License for the specific language governing permissions and
 *        limitations under the License.
 */

package com.edmunds.tools.databricks.maven;

import com.edmunds.tools.databricks.maven.util.ObjectMapperUtils;
import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

@Mojo(name = "prepare-job-resources", requiresProject = true, defaultPhase = LifecyclePhase.PREPARE_PACKAGE)
public class PrepareJobResources extends BaseDatabricksJobMojo {

    private final static String ENVIRONMENT_FILE_NAME = "job-environment.json";

    /**
     * The destination of where to serialize the job environment DTO.
     */
    @Parameter(property = "jobEnvironmentDTOFileOutput", defaultValue = "${project.build" +
            ".directory}/databricks-plugin/" + ENVIRONMENT_FILE_NAME)
    protected File jobEnvironmentDTOFileOutput;

    @Override
    public void execute() throws MojoExecutionException {
        prepareJobEnvironmentDTO();
    }

    void prepareJobEnvironmentDTO() throws MojoExecutionException {
        try {
            FileUtils.writeStringToFile(jobEnvironmentDTOFileOutput,
                    ObjectMapperUtils.serialize(getSettingsUtils().getEnvironmentDTO()),
                    Charset.defaultCharset());
        } catch (IOException e) {
            throw new MojoExecutionException(e.getMessage(), e);
        }
    }
}
