package io.lgtd.dagger.java.module;

import io.dagger.client.Client;
import io.dagger.client.Container;
import io.dagger.client.DaggerQueryException;
import io.dagger.client.Directory;
import io.dagger.module.Base;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class DaggerJavaModule extends Base {
    public DaggerJavaModule(Client dag) {
        super(dag);
    }

    /// Returns a container that echoes whatever string argument is provided
    public Container containerEcho(String stringArg) {
        return dag.container()
                .from("alpine:latest")
                .withExec(List.of("echo", stringArg));
    }

    /// Returns lines that match a pattern in the files of the provided Directory
    public String grepDir(Directory directoryArg, String pattern) throws InterruptedException, ExecutionException, DaggerQueryException {
        return dag.container()
                .from("alpine:latest")
                .withMountedDirectory("/mnt", directoryArg)
                .withWorkdir("/mnt")
                .withExec(List.of("grep", "-R", pattern, "."))
                .stdout();
    }
}
