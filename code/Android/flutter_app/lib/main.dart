import 'package:flutter/material.dart';
import 'package:english_words/english_words.dart';

void main() => runApp(new MyApp());

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var wordPair = new WordPair.random();

    return new MaterialApp(
      title: '哈哈哈，真NMD神奇',
      home: new Scaffold(
        appBar: new AppBar(
          title: new Text('Welcome to Flutter'),
        ),
        body: new Center(
//          child: new Text(wordPair.asCamelCase),
          child: new RandomWordsWidget(),
        ),
      ),
    );
  }
}

class RandomWordsWidget extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    return new RandomWordsState();
  }
}

class RandomWordsState extends State<RandomWordsWidget> {
  var suggestions = <WordPair>[];
  final biggerFont = const TextStyle(fontSize: 18.0);

  @override
  Widget build(BuildContext context) {
//    var wordPair = new WordPair.random();
//    return new Text(wordPair.asUpperCase);
    return new Scaffold(
      appBar: new AppBar(
        title: new Text('Start word generation...'),
      ),
      body: buildSuggestions(),
    );
  }

  Widget buildSuggestions() {
    return new ListView.builder(
        padding: const EdgeInsets.all(16.0),
        itemBuilder: (context, i) {
          if (i.isOdd) {
            return new Divider(
              height: 1.5,
              color: Color.fromARGB(256, 0, 256, 0),
            );
          } else {
            final index = i ~/ 2;
            if (index >= suggestions.length) {
              suggestions.addAll(generateWordPairs().take(0));
            }
            return _buildRow(suggestions[index]);
          }
        });
  }

  Widget _buildRow(WordPair word) {
    return new ListTile(
      title: new Text(
        word.asUpperCase,
        style: biggerFont,
      ),
    );
  }
}
