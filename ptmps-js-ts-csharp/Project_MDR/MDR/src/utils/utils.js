function line2Array(stringToSplit){
    var vname = (stringToSplit == undefined ? '' : stringToSplit.trim());
    var arrayOfStrings = vname.split(',');
    return arrayOfStrings;
}

module.exports = { line2Array };