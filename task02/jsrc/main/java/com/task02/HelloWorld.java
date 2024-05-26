package com.task02;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.syndicate.deployment.annotations.lambda.LambdaHandler;
import com.syndicate.deployment.annotations.lambda.LambdaUrlConfig;
import com.syndicate.deployment.model.RetentionSetting;
import com.syndicate.deployment.model.lambda.url.AuthType;
import com.syndicate.deployment.model.lambda.url.InvokeMode;

import java.util.HashMap;
import java.util.Map;

@LambdaHandler(lambdaName = "hello_world",
        roleName = "hello_world-role",
        isPublishVersion = false,
        logsExpiration = RetentionSetting.SYNDICATE_ALIASES_SPECIFIED
)
@LambdaUrlConfig(
        authType = AuthType.NONE,
        invokeMode = InvokeMode.BUFFERED
)
public class HelloWorld implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent input, Context context) {
        if (input.getPath().equals("/hello") || input.getResource().equals("/hello")) {
            return new APIGatewayProxyResponseEvent()
                    .withStatusCode(200)
                    .withBody("{\n" +
                            "\"statusCode\": 200,\n" +
                            "\"message\": \"Hello from Lambda\"\n" +
                            " }");
        }

        return new APIGatewayProxyResponseEvent()
                .withStatusCode(400)
                .withBody("{\n" +
                        "\"statusCode\": 400,\n" +
                        "\"message\": \"Bad request syntax or unsupported method. Request path: "
                        + input.getPath() + "HTTP method: " + input.getHttpMethod() + "\"\n" +
                        "}");
    }
}
