CocoQuery
=========

A Android library inspire by AndroidQuery, bring chain style UI programming APi to UI development, aim to simplify android development.

Why
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

 ``` java
         aq.id(R.id.icon).image(R.drawable.icon).visible().clicked(this);
         aq.id(R.id.name).text(content.getPname());
         aq.id(R.id.time).text(FormatUtility.relativeTime(System.currentTimeMillis(), content.getCreate())).visible();
         aq.id(R.id.desc).text(content.getDesc()).visible();
 ```

 No nullpoint checking, no boring setter, short code is beautiful, isn't it?


 Why I create a new library rather than continue using AndroidQuery?
 ============

 AndroidQuery was created quite many years ago, try to cover most part of android development including ui,http request,rest and so on, which was good.
 It's might a good open-box solution, but not good enough in all particular area, like url imageview, httprequest. I prefer using Picosso like library to provide better performance, for example.
 Dragger and butterknife bring DI to practical level, I believe it would be mainframe programming style. it would need a better partner.
 The last reason is AndroidQuery is not well maintained any more.

 So, I want to create a library concentrate to simplify UI layer programming, can be integrated well with other libraries.


