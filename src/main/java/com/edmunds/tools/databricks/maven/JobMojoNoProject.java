/*
 *  Copyright 2018 Edmunds.com, Inc.
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 */

package com.edmunds.tools.databricks.maven;

import com.edmunds.tools.databricks.maven.model.EnvironmentDTO;
import com.edmunds.tools.databricks.maven.util.EnvironmentDTOSupplier;
import java.io.File;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

/**
 * Controls a given databricks job [start\stop\restart].
 * <p>
 * NOTE 1: If a job does not have a unique name, it will fail unless failOnDuplicateJobName=false,
 * in which case only the first one will be updated.
 * </p>
 * NOTE 2: If a job has more than 1 active run, ALL of them will be cancelled on STOP\RESTART.
 * <p>
 * NoProject is split out so that we have a mojo that will work with multi module projects.
 * </p>
 */
@Mojo(name = "job-np", requiresProject = false)
public class JobMojoNoProject extends JobMojo {

    /**
     * The serialized environment dto is required to be passed in a NoProject scenario.
     */
    @Parameter(name = "environmentDTOFile", property = "environmentDTOFile", required = true)
    File environmentDTOFile;

    @Override
    protected EnvironmentDTOSupplier getEnvironmentDTOSupplier() {
        return () -> {
            EnvironmentDTO serializedEnvironment = EnvironmentDTO.loadEnvironmentDTOFromFile(environmentDTOFile);
            //We now set properties that are based on runtime and not buildtime. Ideally this would be enforced.
            //I consider this code ugly
            if (environment != null) {
                serializedEnvironment.setEnvironment(environment);
            }
            return serializedEnvironment;
        };
    }
}
