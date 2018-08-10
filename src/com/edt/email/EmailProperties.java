package com.edt.email;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Properties;

public class EmailProperties
{
	private static String fname = "myProperties";
	
	public static Properties props = null;
	
	private static File propertiesFile = null;
	
	public static void setProperties(String fname)
	{
	    EmailProperties.fname = fname;
	}

    public static void init()
    {
        FileInputStream propFile = null;
        
//        Properties p = null;
        
        try
        {
            EmailProperties.propertiesFile = new File(EmailProperties.fname);
            
            System.out.println("Looking for " + EmailProperties.fname);
            
            if (EmailProperties.propertiesFile.exists())
            {
// set up new properties object from file "myProperties"
                propFile = new FileInputStream(EmailProperties.fname);
                
                EmailProperties.props = new Properties(System.getProperties());            
                EmailProperties.props.load(propFile);
            }
            else
                System.out.println(EmailProperties.fname + " does NOT exist");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

// set the system properties
        System.setProperties(EmailProperties.props);
    }
    
    public static boolean update()
            throws IOException
    {
        boolean rc = ((null != EmailProperties.props) && (null != EmailProperties.propertiesFile));
        
        if (rc)
        {
            String comment = "# " + new Date() + "; updated email & password";

            OutputStream out = new FileOutputStream(EmailProperties.propertiesFile);
            
            EmailProperties.props.store(out, comment);
            
            System.out.println("\t- " + comment);
        }
        
        return (rc);
    }
}
