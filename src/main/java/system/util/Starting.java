package system.util;

import io.vertx.core.*;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import system.dao.PostgresClient;

@SuppressWarnings("unused")
public class Starting extends AbstractVerticle {


    @Override
    public void start() throws Exception {
        super.start();

        vertx.deployVerticle(WebServerVerticle.class.getName(), it -> {
            if (it.failed()) {
                printErrorMessage(it, "WebServerVerticle");
                Runtime.getRuntime().halt(-1);
            } else {

                System.out.println("WebServerVerticle deployed.");
            }
        });
    }

    @SuppressWarnings({"SameParameterValue", "unused"})
    private void printErrorMessage(AsyncResult<String> stringAsyncResult, String failedDeployVerticleName) {
        System.out.println(
                "Failed to deploy : " + failedDeployVerticleName +
                        "\n\t result:  " + stringAsyncResult.result() +
                        "\n\t cause: " + stringAsyncResult.cause() +
                        "\n\t stackTrace: "
        );
        stringAsyncResult.cause().printStackTrace();
    }
}
