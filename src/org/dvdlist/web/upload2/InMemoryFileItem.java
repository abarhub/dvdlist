package org.dvdlist.web.upload2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.logging.Logger;

import org.apache.commons.fileupload.FileItem;
 
public class InMemoryFileItem implements FileItem  {
	  /**
	 * 
	 */
	private static final long serialVersionUID = 943184997429052202L;
	private final static Logger LOGGER = Logger.getLogger(InMemoryFileItem.class.getName());
	  public static final String DEFAULT_CHARSET = "ISO-8859-1";
	  private String fieldName;
	  private String contentType;
	  private boolean isFormField;
	  private String fileName;
	  private byte[] content = new byte[0];
	 
	  /**
	   * Constructs a new
	   * <code>GaeFileItem</code> instance.
	   *
	   * @param fieldName The name of the form field.
	   * @param contentType The content type passed by the browser or
	   * <code>null</code> if not specified.
	   * @param isFormField Whether or not this item is a plain form field, as opposed to a file upload.
	   * @param fileName The original filename in the user's filesystem, or
	   * <code>null</code> if not specified.
	   */
	  public InMemoryFileItem(String fieldName,
	          String contentType, boolean isFormField, String fileName) {
	    this.fieldName = fieldName;
	    this.contentType = contentType;
	    this.isFormField = isFormField;
	    this.fileName = fileName;
	  }
	 
	  @Override
	  public InputStream getInputStream()
	          throws IOException {
	    return new ByteArrayInputStream(content);
	  }
	 
	  @Override
	  public String getContentType() {
	    return contentType;
	  }
	 
	  @Override
	  public String getName() {
	    return fileName;
	  }
	 
	  @Override
	  public boolean isInMemory() {
	    return true;
	  }
	 
	  @Override
	  public long getSize() {
	    return content.length;
	  }
	 
	  @Override
	  public byte[] get() {
	    return content;
	  }
	 
	  @Override
	  public String getString(final String charset)
	          throws UnsupportedEncodingException {
	    return new String(get(), charset);
	  }
	 
	  @Override
	  public String getString() {
	    try {
	      return new String(content, DEFAULT_CHARSET);
	    } catch (UnsupportedEncodingException e) {
	      return new String(content);
	    }
	  }
	 
	  @Override
	  public void write(File file) throws Exception {
	    /*FileOutputStream fout = null;
	    try {
	      fout = new FileOutputStream(file);
	      fout.write(get());
	    } finally {
	      if (fout != null) {
	        fout.close();
	      }
	    }*/
	  }
	 
	  @Override
	  public void delete() {
	    content = new byte[0];
	  }
	 
	  @Override
	  public String getFieldName() {
	    return fieldName;
	  }
	 
	  @Override
	  public void setFieldName(String fieldName) {
	    this.fieldName = fieldName;
	  }
	 
	  @Override
	  public boolean isFormField() {
	    return isFormField;
	  }
	 
	  @Override
	  public void setFormField(boolean state) {
	    isFormField = state;
	  }
	 
	  @Override
	  public OutputStream getOutputStream()
	          throws IOException {
	 
	    return new ByteArrayOutputStream() {
	 
	      @Override
	      public synchronized void reset() {
	        super.reset();
	        content = toByteArray();
	      }
	 
	      @Override
	      public void flush() throws IOException {
	        super.flush();
	        content = toByteArray();
	      }
	 
	      @Override
	      public void close() throws IOException {
	        super.close();
	        content = toByteArray();
	      }
	    };
	  }
}
