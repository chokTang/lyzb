package com.lyzb.jbx.mvp.view.home.first;

import com.lyzb.jbx.model.params.FileBody;

import java.util.List;

public interface ISendMessageView {
    void OnUploadResult(List<FileBody> fileBodies);
}
