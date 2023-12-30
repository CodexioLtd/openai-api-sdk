# Contributing

This project is in its very early stage and contributors are very welcomed. If you feel that something has to be
changed or a bug to be fixed, you can report a [new issue](https://github.com/CodexioLtd/openai-api-sdk/issues/new), and
we can take care of it.

If you want to submit directly a code fix, we will be more than glad to see it. Fork the repository and start a clean
branch out of the version you want to patch. When you are finished, make sure all your tests are passing and the
coverage remains in decent level by executing `mvn clean test jacoco:report -Pmvn-deploy`.

Please, use the [code style](https://github.com/CodexioLtd/openai-api-sdk/blob/master/cdx-openai-codestyle.xml)
in the project root folder. If your IDE does not support it, we strongly encourage you just to follow
the code styling in the rest of the classes and methods.

After all your tests are passing and the coverage seems good to you, create a
[pull request](https://github.com/CodexioLtd/openai-api-sdk/compare). We will review the request and either leave
some meaningful suggestions back or maybe merge it and release it with the next release.

The first open beta of the project is 0.8.0 and all the way up to 1.0.0 we will strive to find core
contributors who will serve as project maintainers in the future. [Codexio Ltd.](https://codexio.bg) will remain
the main project maintainer.
