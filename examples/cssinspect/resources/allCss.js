var allRules = [];
var sSheetList = document.styleSheets;
for (var sSheet = 0; sSheet < sSheetList.length; sSheet++)
{
    var ruleList = document.styleSheets[sSheet].cssRules;
    for (var rule = 0; rule < ruleList.length; rule ++)
    {
       allRules.push( ruleList[rule].selectorText );
    }
}
