package com.sam.alura.notification;

import com.sam.alura.course.NewFeedBackRequest;

public interface NotificationService {

    void send(NewFeedBackRequest request);
}
