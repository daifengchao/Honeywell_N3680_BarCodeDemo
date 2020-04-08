#  This app is a sample app that teaches you how to receive the scan result of the Honeywell N3680 module on the AIM-65AT device and how to turn on/off the scanning prompt tone.

##  If you want to use the app, first make sure that your device has Honeywell N3680 module and N3680BarCode app is installed. If your system does not have N3680BarCode app installed, please install N3680BarCode.apk in the current directory first.

##  If you want to develop your own app to receive the scan results of the Honeywell N3680 module, you can refer to the following method.

### Make sure your system has N3680BarCode app installed. If your system does not have N3680BarCode app installed, please install N3680BarCode.apk in the current directory first.

### The method to start the Service App is as follows:

    private static final String SERVICE_PACKAGE_NAME = "com.advantech.n3680barcode";
    private static final String SERVICE_CLASS_NAME = "com.advantech.n3680barcode.RunN3680BarcodeService";
    private static final String ACTION_START_SERVICE = "com.advantech.n3680barcode.START_SERVICE";

    Intent intent = new Intent(ACTION_START_SERVICE);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            intent.setComponent(new ComponentName(SERVICE_PACKAGE_NAME,SERVICE_CLASS_NAME));
        }
        sendBroadcast(intent);

### The method to open the scanning prompt sound is as follows:

    private static final String ACTION_TURNON_BEEP = "com.advantech.n3680barcode.TURNON_BEEP";
    Intent intent = new Intent(ACTION_TURNON_BEEP);
    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    sendBroadcast(intent);
    
### The method to close the scanning prompt sound is as follows:

    private static final String ACTION_TURNOFF_BEEP = "com.advantech.n3680barcode.TURNOFF_BEEP";
    Intent intent = new Intent(ACTION_TURNOFF_BEEP);
    intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
    sendBroadcast(intent);
    
### The method of receiving scan results is as follows:

    private static final String ACTION_TRANSFER_DATA = "com.advantech.n3680barcode.TRANSFER_DATA";
    BarCodeDataBroadcastReceiver barCodeDataBroadcastReceiver;

    IntentFilter filter = new IntentFilter();
    filter.addAction(ACTION_TRANSFER_DATA);
    barCodeDataBroadcastReceiver = new BarCodeDataBroadcastReceiver();
    registerReceiver(barCodeDataBroadcastReceiver,filter);

    private  class BarCodeDataBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String barcodeData = intent.getStringExtra("barcodeData");
            if(barcodeData != null){
                textView.append(barcodeData + "\n");
            }
        }
    }
