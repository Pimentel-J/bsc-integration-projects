namespace MDV
{
    
    class Config {    
        const string mdrLocalURL = "http://localhost:3000";
        const string mdrCloudURL = "https://***private***.herokuapp.com";
        const string apiPrefix = "/api/";
        

        public static string WebApiApplicationJson() 
        {
            return  (mdrCloudURL + apiPrefix);
        }
    }
}