# GeckoBoard Dataset API

[Geckoboard](https://www.geckoboard.com) is business dashboard tool that links with serveral online providers and also provides a http based custom dataset API. 

This Java lib will help in the creating and publishing of datasets to the GeckoBoard Dataset API. Details for the API can be found here: https://developer.geckoboard.com/api-reference/curl/ 

[JavaDocs](https://eau-claire-energy-cooperative.github.io/geckoboard-dataset-lib/) for this project are available via GitHub Pages.

## Usage

A simple example of sending data to the Geckoboard is below. For more advanced usage check the [javadocs](https://eau-claire-energy-cooperative.github.io/geckoboard-dataset-lib/). 

```

Geckoboard g = new Geckoboard("api_key");

//test the api key
if(g.test())
{
    System.out.println("Guess it works");
}
else
{
    System.out.println("Maybe your api key is wrong");
}

//create a new dataset
g.create("dataset_name", new FieldDefinition(DataType.STRING,"color","A Color"),new FieldDefinition(DataType.NUMBER,"frequency"));

//replace all data in a dataset
List<DataRow> aList = new ArrayList<DataRow>();

DataRow aRow = new DataRow().addData("color","Blue").addData("frequency",10);

aList.add(aRow);
g.replace("dataset_name",aList);

//append a row to a dataset
g.append("dataset_name",aRow);

```

## Date and Timestamps

Using the data and timestamp data types requires that you add the data to the DataRow class a big differently. This is because you need to pass in a JodaTime [DateTime object](https://www.joda.org/joda-time/apidocs/org/joda/time/DateTime.html) so that it can be parsed correctly. 

```
DateTime dateValue = new DateTime();
DateTime timeValue = new DateTime();

DataRow aRow = new DataRow();
aRow.addData(DataType.DATE,"date",dateValue);
aRow.addData(DataType.DATETIME,"time",timeValue);

```