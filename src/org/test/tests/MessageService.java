package org.test.tests;

import org.test.annotations.Injectable;

@Injectable
public class MessageService {
    public String getMessage() {
        return "Hello world!";
    }
}
