package net.mednikov.jtwigvertx;

import java.util.Map;

import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.common.template.TemplateEngine;

class JtwigTemplateEngine implements TemplateEngine {

    private JtwigTemplate getTemplate(String filename){
        return JtwigTemplate.classpathTemplate(filename);
    }

    @Override
    public void render(Map<String, Object> data, String filename, Handler<AsyncResult<Buffer>> handler) {
        try {
            JtwigTemplate template = getTemplate(filename);
            JtwigModel model = JtwigModel.newModel(data);
            Buffer buffer = Buffer.buffer(template.render(model));
            handler.handle(Future.succeededFuture(buffer));
        } catch (Exception ex){
            ex.printStackTrace();
            handler.handle(Future.failedFuture(ex));
        }
    }

    @Override
    public boolean isCachingEnabled() {
        return false;
    }

}