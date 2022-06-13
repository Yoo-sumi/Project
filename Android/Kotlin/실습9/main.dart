import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_app/list_page.dart';


class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Navigation Test',
      home: ListPage(),
    );
  }
}

void main() => runApp(MyApp());
