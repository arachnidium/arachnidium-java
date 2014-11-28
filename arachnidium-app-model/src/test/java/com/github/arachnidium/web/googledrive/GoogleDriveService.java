package com.github.arachnidium.web.googledrive;

import com.github.arachnidium.core.Handle;
import com.github.arachnidium.core.fluenthandle.HowToGetHandle;
import com.github.arachnidium.model.common.Application;
import com.github.arachnidium.model.common.Static;

public class GoogleDriveService<S extends Handle, U extends HowToGetHandle>  extends Application<S, U> {

	@Static
	public LoginToGoogleService<?> login;
	@Static
	public DocumentList<?> documentList;
	@Static
	public Document<?> document;
	@Static
	public SpreadSheet<?> sheet;
	
	protected GoogleDriveService(S handle) {
		super(handle);
	}

}
