package com.javatechnics.utilities.filerenamer;

/*
 * Copyright 2001-2005 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;

/**
 * Goal which renames a file
 */
@Mojo( name = "rename", defaultPhase = LifecyclePhase.PACKAGE )
public class RenamerMojo
    extends AbstractMojo
{
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;
    
    @Parameter ( defaultValue = "${project.build.directory}/${project.artifactId}-${project.version}.nar", required = true )
    private File inputFileName;
    
    @Parameter ( defaultValue = "${project.build.directory}/${project.artifactId}-${project.version}.jar", required = true )
    private File outputFileName;

    public void execute()
        throws MojoExecutionException
    {
        getLog().info("Staring filerenamer");
        getLog().info("Output filename: " + outputFileName.getAbsolutePath());
        getLog().info("Input filename: " + inputFileName.getAbsolutePath());
        File f = outputDirectory;
        
        if (outputFileName.exists()){
            getLog().info("Input file " + outputFileName.toString() + " exists. It will be deleted!");
            if (outputFileName.delete()){
                getLog().info("Deleted " + outputFileName.toString());
                if (inputFileName.renameTo(outputFileName)){
                    getLog().info("Successfully renamed file");
                } else {
                    getLog().info("Unable to rename file.");
                }
            } else {
                getLog().info("Could not delete file!");
            }
        }
    }
}
