var regex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
var System = Java.type('java.lang.System');

expert.newRule()
    .ifMatch('content', regex)
    .thenEquals('content-type', 'url')
    .build();

expert.newRule()
    .ifNotMatch('content', regex)
    .thenNotEquals('content-type', 'url')
    .build();

System.out.println("Example 1:");

expert.clearFacts();

expert.addFact('content', 'http://www.google.com');

System.out.println('Before');
System.out.println(expert.getKnowledgeBase());

expert.infer();

System.out.println('');
System.out.println('After');
System.out.println(expert.getKnowledgeBase());

System.out.println("=================================================");

System.out.println("Example 2:");

expert.clearFacts();

expert.addFact('content', 'google-com');

System.out.println('Before');
System.out.println(expert.getKnowledgeBase());

expert.infer();

System.out.println('');
System.out.println('After');
System.out.println(expert.getKnowledgeBase());



