CocoQuery
=========

An Android library inspire by AndroidQuery, bring chain style UI programming APi to UI development, aim to simplify android UI development.

Motivation
=========
I love the way develop android UI logic with AndroidQuery. Compare those code snippet

``` java

        ImageView tbView = (ImageView) view.findViewById(R.id.icon);
        if(tbView != null){

                tbView.setImageBitmap(R.drawable.icon);
                tbView.setVisibility(View.VISIBLE);

                tbView.setOnClickListener(this);

        }

        TextView nameView = (TextView) view.findViewById(R.id.name);
        if(nameView != null){
                nameView.setText(content.getPname());
        }

        TextView timeView = (TextView) view.findViewById(R.id.time);

        if(timeView != null){
                long now = System.currentTimeMillis();
                timeView.setText(FormatUtility.relativeTime(now, content.getCreate()));
                timeView.setVisibility(View.VISIBLE);
        }

        TextView descView = (TextView) view.findViewById(R.id.desc);

        if(descView != null){
                descView.setText(content.getDesc());
                descView.setVisibility(View.VISIBLE);
        }
 ```

 After

 ``` java
         aq.id(R.id.icon).image(R.drawable.icon).visible().clicked(this);
         aq.id(R.id.name).text(content.getPname());
         aq.id(R.id.time).text(FormatUtility.relativeTime(System.currentTimeMillis(), content.getCreate())).visible();
         aq.id(R.id.desc).text(content.getDesc()).visible();
 ```

 No nullpoint checking, no boring setter, short code is beautiful, isn't it?


 Why another wheel?
=========

 AndroidQuery was created quite many years ago, try to cover most part of android development including ui,http request,rest and so on, which was good.
 
 It's might a good open-box solution, but not good enough in all particular area, like url imageview, httprequest. I prefer using Picosso like library to provide better performance, for example.
 
butterknife bring DI to practical level, I believe it would be mainstream programming style,and it would need a better partner.

 Despite of those, AndroidQuery has not update for quite long time.

 So, I decided to create a library concentrate to simplify UI layer programming, and can be integrated well with other libraries.


 Usage
========

```xml
 compile 'com.cocosw:query:0.+'
```

or 

```xml
 compile 'com.cocosw.query:ext:0.+'
```

or just grab the jar file into your libs folder

Instant CocoQuery in your Activity or Fragment

``` java
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        q = new CocoQuery(this);
```
or in Fragment
``` java
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(layoutId(), null);
        CocoQuery q = new CocoQuery(getActivity(),v);
```

please *NEVER* keep CocoQuery instance into static field.

 CocoQuery is NOT a
=========

#####Async http request library
 There are already tons of libraries doing this, you can feel free to choose the one you like, for example https://github.com/kevinsawicki/http-request, chain style programming.
 
#####Image downloading library
 I understand this is important for you, still, other libraries, like Picasso mentioned before can do this quite well.
 
 If you want to bring use Picasso with CocoQuery style, like q.id(R.id.image).image("http://img"); you can use ext artifact
 
 Dependency & Compatibility
=========
 - *NO* any other dependency.
 - Most of api compatible with AndroidQuery, and ajax related api been removed.
 - Android Api 8+
 
 For ext artificat:
 - OKHttp 1.5 and Picasso 2.2
 
Extension
=========
 You can extend the class to adapt your custom view or 3rd party libraries.

#####Extend AbstractViewQuery

``` java
public class YourQuery extends AbstractViewQuery<DefaultQuery> {

   // constructor.....

    /**
     * Your own method to chain
     *
     * @param resid the resid
     * @return self
     */
    public T image(String url) {
        if (view instanceof ImageView) {
            Picasso.with(context).load(url).into((ImageView)view);
        }
        return self();
    }
 ```

#####Build CocoQuery
You only need to do this only one time, so onCreate in Application would be a ideal place

``` java
CocoQuery.setQueryClass(YourQuery.class);
```

#####And
``` java
CocoQuery<YourQuery> q = new CocoQuery(activity);
```

You can also extend CocoQuery for other functions you need.


License
==========
Apache License, Version 2.0


Contribution
==========
There are many codeline directly copy from AndroidQuery https://github.com/androidquery/androidquery
