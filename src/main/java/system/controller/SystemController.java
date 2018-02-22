package system.controller;

import lombok.Getter;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public class SystemController {
    @Getter
    private Router router;

    public SystemController(Vertx vertx) {
        router = Router.router(vertx);
        registerRoutes();
    }

    private void registerRoutes() {
        router.route(HttpMethod.GET, "/").handler(routingContext -> {
            routingContext.response().sendFile("webroot/index.html");
        });
    }
}
