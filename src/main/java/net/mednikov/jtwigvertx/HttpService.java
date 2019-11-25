package net.mednikov.jtwigvertx;

import java.util.HashMap;
import java.util.Map;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServer;
import io.vertx.ext.web.Router;

class HttpService extends AbstractVerticle{

    @Override
    public void start(Promise<Void> promise) throws Exception {
        JtwigTemplateEngine templateEngine  = new JtwigTemplateEngine();
        HttpServer server = vertx.createHttpServer();
        Router router = Router.router(vertx);
        router.get("/").handler(context->{
            Map<String, Object> data = new HashMap<>();
            data.put("name", "Yuri");
            templateEngine.render(data, "template/hello.twig", res->{
                if (res.succeeded()){
                    context.response().end(res.result());
                }
            });
        });
        server.requestHandler(router);
        server.listen(4567, res->{
            if (res.succeeded()){
                promise.complete();
            } else {
                promise.fail(res.cause());
            }
        });
    }

    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(new HttpService());
    }
}