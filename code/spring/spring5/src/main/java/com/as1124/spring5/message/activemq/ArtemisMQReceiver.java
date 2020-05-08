package com.as1124.spring5.message.activemq;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("activemq")
@Component
public class ArtemisMQReceiver {
}
