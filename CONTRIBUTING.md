# Contributing

When contributing to this repository, please first discuss the change you wish to make via issue,
email, or any other method with the owners of this repository before making a change. 

## We Develop with Github
We use github to host code, to track issues and feature requests, as well as accept pull requests.

### Customizing .git/config
Please make sure that you are using a valid github account when contributing to this repo.
If the user/password differs then that of your global .git/config, 
add following the .git/config of your repo:
```yaml
[user]
	name = {git user}
	email = {git email}
```


## We Use [Github Flow](https://guides.github.com/introduction/flow/index.html), So All Code Changes Happen Through Pull Requests
Pull requests are the best way to propose changes to the codebase (we use [Github Flow](https://guides.github.com/introduction/flow/index.html)). We actively welcome your pull requests:

1. Fork the repo and create your branch from `master`. Branch names should be like issue5/updating-workspace-service.
2. If you've added code that should be tested, add tests.
3. If you've changed APIs, update the README.
4. Ensure the test suite passes.
5. Run any integration tests and make sure they pass.
6. Issue that pull request!
7. Pull requests should be reviewed by at least 1 other developer before being merged

## Any contributions you make will be under the Apache License 2.0
In short, when you submit code changes, your submissions are understood to be under the same [Apache License 2.0](https://choosealicense.com/licenses/apache-2.0/) that covers the project. Feel free to contact the maintainers if that's a concern.

## Report bugs and request features using Github's [issues](https://github.com/edmunds/databricks-maven-plugin/issues)
We use GitHub issues to track public bugs. Report a bug or request a new feature by [opening a new issue](); it's that easy!

### Write bug reports with detail, background, and sample code

**Great Bug Reports** tend to have:

- A quick summary and/or background
- Steps to reproduce
  - Be specific!
  - Give sample code if you can.
- What you expected would happen
- What actually happens
- Notes (possibly including why you think this might be happening, or stuff you tried that didn't work)

People *love* thorough bug reports. I'm not even kidding.

## Making Releases to Maven Central
Only Certain Edmunds Developers will be able to make releases of a new version to Maven Central.
Here are the steps to be able to make a release:
### Setup
1. Create an account on issues.sonatype.org
2. Respond to this ticket saying that your username needs permission:
https://issues.sonatype.org/browse/OSSRH-42546
3. Add to your ~/.m2/settings.xml
    <server>
      <id>ossrh</id>
      <username>{sonatype username}</username>
      <password>{sonatype password}</password>
    </server>
4. [Install GPG](https://central.sonatype.org/pages/working-with-pgp-signatures.html) and make a key.
5. Upload your key to a couple of servers:
```bash
gpg --keyserver hkp://pool.sks-keyservers.net --send-keys ${key-id}
gpg --keyserver hkp://keyserver.ubuntu.com --send-keys ${key-id}
```
### Performing a Release
Execute
```bash
mvn release:clean release:prepare release:perform -Pdeploy
```
[Verify](https://oss.sonatype.org/#nexus-search;quick~databricks-maven-plugin)

## Use a Consistent Coding Style
We use a slight modification of google java style.
Please configure your setup to use the checkstyle/google_checkstyle.xml files
(For Intellij users, see the section on [setting up intellij checkstyle](#setting-up-intellij-checkstyle))
Currently, builds are configured to fail if style requirements are not met.

# Setting Up Intellij Checkstyle
1) Import Formatter Settings
    - Navigate to Settings -> Editor -> Code Style -> Java
    - Click on cogwheel near the "Scheme" selector
    - Choose "Import Scheme" -> "Intellij IDEA code style XML"
    - Select databricks-maven-plugin/checkstyle/google-idea-checkstyle.xml file
2) Install CheckStyle Plugin
    - Instructions on how to do so can be found [here](https://medium.com/@jayanga/how-to-configure-checkstyle-and-findbugs-plugins-to-intellij-idea-for-wso2-products-c5f4bbe9673a)
    or just
    - Ctrl + Shift + A -> enter "plugins" -> go to "Marketplace" tab
    - Find "CheckStyle-IDEA" plugin, install it and restart IDEA
3) Configure CheckStyle Plugin
    - Navigate to Preferences -> Other Settings -> Checkstyle
    - Click "+" under Configuration File to add a new configuration
    - Add a relevant description
    - Click the "Use a local Checkstyle file" radio button
    - Click "Browse" and navigate to the databricks-maven-plugin/checkstyle/google_checkstyle.xml file
    - The new checkstyle file should now be listed under "Configuration File", check it and apply
4) Run the "Checkstyle real-time scan"
    - Right click on a file you would like to apply the checkstyle to
    - Navigate to Analyze -> Run Inspection By Name
    - In the search bar that appears, type "Checkstyle real-time scan"
    - You can now select to run the checkstyle on files of your choosing
5) Fix checkstyle errors that the scan detects

## References
This document was adapted from the open-source contribution guidelines for [Transcriptase](https://gist.github.com/briandk/3d2e8b3ec8daf5a27a62)