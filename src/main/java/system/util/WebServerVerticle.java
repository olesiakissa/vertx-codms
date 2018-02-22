package system.util;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.asyncsql.PostgreSQLClient;
import io.vertx.ext.sql.SQLClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.CookieHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.handler.StaticHandler;
import io.vertx.ext.web.sstore.LocalSessionStore;
import system.controller.SystemController;
import system.dao.PostgresClient;

public class WebServerVerticle extends AbstractVerticle {

    PostgresClient postgresClient;

    @Override
    public void start(Future<Void> future) {
        int port = config().getInteger("http.port", 3000);
        final String postgresUser = config().getString("postgres_user");
        final String postgresPassword = config().getString("postgres_password");
        final String postgresUrl = config().getString("postgres_url");
        //Fix npe
        JsonObject postgreSQLClientConfig = new JsonObject().put(postgresUrl, port);
        SQLClient postgreSQLClient = PostgreSQLClient.createShared(vertx, postgreSQLClientConfig);

        postgreSQLClient.getConnection( res -> {
            if (res.succeeded()) {
                SQLConnection connection = res.result();
                //postgresClient.createPrimaryData(postgresUrl, postgresUser, postgresPassword);
            } else {
                System.out.println("Error occured while creating primary data.");
            }
        });
        Router router = Router.router(vertx);
        router.route("/*").handler(StaticHandler.create("webroot"));
        router.route().handler(BodyHandler.create());
        router.route().handler(CookieHandler.create());
        router.route().handler(SessionHandler.create(LocalSessionStore.create(vertx)));

        router.mountSubRouter("/", new SystemController(vertx).getRouter());

        vertx
                .createHttpServer().requestHandler(router::accept)
                .listen(port, result -> {
                    if (result.succeeded()) {
                        future.complete();
                    } else {
                        future.fail(result.cause());
                    }
                });

    }
}