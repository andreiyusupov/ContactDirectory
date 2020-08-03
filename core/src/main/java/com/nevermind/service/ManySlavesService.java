package com.nevermind.service;

import java.util.List;

public interface ManySlavesService<T> extends CoreService<T> {
    List<T> getAllByMasterId(long masterId);
}
