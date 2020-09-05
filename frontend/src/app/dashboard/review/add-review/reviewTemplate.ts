export const reviewTemplate = (user, paperTitle) => `
<review xmlns="https://github.com/milica152/XML_tim27"
 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
 xsi:schemaLocation="https://github.com/milica152/XML_tim27 ../../../../../../Backend/src/main/resources/static/schemas/Review.xsd">
    <title>${paperTitle}</title>
    <id></id>
    <reviewer>
        <name>${user.name}</name>
        <surname>${user.surname}</surname>
        <profession>${user.profession}</profession>
        <contact>${user.contact}</contact>
    </reviewer>
    <authors><author><name>s</name><surname>s</surname><profession>s</profession><contact>s</contact></author></authors>
    <questionnaire>
        <item>
            <question>Da li je naslov rada dobro izabran?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li apstrakt sadrži prave podatke o radu?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je uvod uspešno privukao Vašu pažnju (generalni koncept je dobro uveden i rad dobro motivisan, a
ciljevi rada eksplicitno navedeni)?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je struktura rada adekvatna?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je rad lak za čitanje?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je moguće razumevanje teksta bez predznanje?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je u radu navedena odgovarajuća literatura?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li su u radu reference korektno navedene?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li su slike i tabele funkcionalne i adekvatne? Da li su sve navedene slike i tabele neophodne? Da li su
dovoljno vidljive (dobra rezolucija, slova nisu previše sitna,...)?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li sve što je navdeno u zaključku sledi iz tela rada?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je terminologija korektna? Da li su autori demonstrirali poznavanje polja?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li ima suštinskih grešaka i propusta?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je rad adekvatnog obima?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
        <item>
            <question>Da li je rad adekvatno formatiran?</question>
            <answer>Odgovor</answer>
            <comment_example>Insert comment or example</comment_example>
        </item>
    </questionnaire>
    <comments>
        <comment>Insert comment</comment>
    </comments>
    <paperRating>Insert paper rating</paperRating>
    <recommendation>Insert recommendation</recommendation>
</review>
`;
