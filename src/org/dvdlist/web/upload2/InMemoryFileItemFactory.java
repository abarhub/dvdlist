package org.dvdlist.web.upload2;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
 
public class InMemoryFileItemFactory implements FileItemFactory  {
	
	  @Override
	  public FileItem createItem(String fieldName, String contentType, boolean isFormField, String fileName) {
	    return new InMemoryFileItem(fieldName, contentType, isFormField, fileName);
	  }
}
