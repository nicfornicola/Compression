public class RandString
{

	public String r(String type, int l)
	{
		String str = "" + l;
		return r(type, str);
	}
	
    public String r(String type, String l)
	{
        int length = Integer.parseInt(l);

		//start with an empty string
		String str = "";
		int rand = 0;

		if(type.equals("upper"))
		{
			for(int p = 0; p < length; p++)
			{
				//get a random ascii value in between 32 and 127 
				rand = (int)(Math.random() * ((90 - 65) + 1)) + 65;
				//convert the int->char->string and add it the existing string
				str += Character.toString(rand);

			}
		}

		if(type.equals("lower"))
		{
			for(int p = 0; p < length; p++)
			{
				//get a random ascii value in between 32 and 127 
				rand = (int)(Math.random() * ((122 - 97) + 1)) + 97;
				//convert the int->char->string and add it the existing string
				str += Character.toString(rand);

			}
		}

		if(type.equals("rubbish"))
		{
			for(int p = 0; p < length; p++)
			{
				//get a random ascii value in between 32 and 127 
				rand = (int)(Math.random() * ((127 - 32) + 1)) + 32;
				//convert the int->char->string and add it to the existing string
				
				str += Character.toString(rand);

			}
		}
		
		return str;
    }

    //args should be type then nlength
    public static void main(String[] args) 
	{
		
    }

}