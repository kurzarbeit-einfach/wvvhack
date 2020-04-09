<?php include('header_beta.php'); ?>

<div class="container conversation">
    <div class="inner">
        <h2>Formular zur Anzeige von Kurzarbeitergeld &ndash; ich helfe dir dabei!</h2>
        <div id="my-botui-app">
            <bot-ui></bot-ui>
            <div id="backButton" style="text-align: right; margin-top: 15px; display: none;">
                <a href="#" onclick="goBack(); return false;" style="font-size: 15px;">Vorherige Frage nochmal</a>
            </div>
        </div>
    </div>
</div>
<style>
    html .fancybox-slide--iframe .fancybox-content {
        max-width: 400px;
        max-height: 600px;
    }
</style>
<div class="container thanks">
    <div class="inner">
        <div class="part_1">
            <h2>Dein Formular ist fertig!</h2>
            <p>
                Das wäre geschafft, auf Basis deiner Antworten habe ich für dich das Formular zur Anzeige der Kurzarbeit für die Bundesagentur für Arbeit komplett ausgefüllt!<br/><br/>
                <strong>Disclaimer:</strong><br/>
                Bitte beachte, dass diese Webseite keine Rechtsberatung tätigt und wir für die Richtigkeit der in der nachfolgend downloadbaren PDF keine Gewähr übernehmen.<br/>
                Zwar haben wir diese Webseite unter bestmöglicher Absprache, auch mit Unterstützung von Rechtsanwälten erstellt, die Verwendung des Formulars geschieht aber auf eigenes Risiko. Aus diesem Grund kannst du alle Angaben im Formular auch nach dem Download direkt im PDF nochmal ändern.
            </p>
            <p>
                <button class="download">Verstanden! Jetzt Formular herunterladen (PDF)</button>
            </p>
        </div>
        
        <div class="part_2" style="display: none;">
            <h2>So geht es nun weiter</h2>
            <p>
                Du hast das Formular erfolgreich heruntergeladen. Zeit, die letzten Schritte durchzuführen:
            </p>
            <ol>
                <li>
                    Überprüfe das Formular, drucke es aus, unterschreibe es und scanne es wieder ein.
                </li>
                <li>
                    Du wirst das Formular gleich direkt online zur Bundesagentur für Arbeit übertragen. Dazu benötigst du einen Zugang zu den eServices der Arbeitsagentur.
                    Hast du diesen bereits (z.B. weil du die Jobbörse benutzt), kannst du direkt zum nächsten Schritt gehen.
                    Wenn du noch keinen Zugang zu den eServices hast, dann rufe jetzt bitte die Rufnummer <strong>0800 4 5555 20</strong> an und frage nach einem Zugang zu den eServices um online Kurzarbeitergeld zu beantragen.
                    Man wird dir innerhalb von ein paar Minuten Zugangsdaten per E-Mail zusenden.
                </li>
                <li>
                    Melde dich mit deinen Zugangsdaten unter <a href="https://anmeldung.arbeitsagentur.de/portal" target="_blank">https://anmeldung.arbeitsagentur.de/portal</a> an.<br/>
                    <a data-fancybox="step2" href="/assets/images/step2.jpg"><img src="/assets/images/step2.jpg" style="max-width: 200px; max-height: 200px;" alt="Schritt 2"></a>
                </li>
                <li>
                    Klicke im rechten Bereich bei "Direkter Upload" auf den Button "DIREKT HOCHLADEN".<br/>
                    <a data-fancybox="step3" href="/assets/images/step3.jpg"><img src="/assets/images/step3.jpg" style="max-width: 200px; max-height: 200px;" alt="Schritt 2"></a>
                </li>
                <li>
                    Kontrolliere deine persönlichen Daten, die nun angezeigt werden. Dann auf WEITER klicken.
                </li>
                <li>
                    Wähle nun "Kurzarbeitergeld-Anzeige" aus und folge den weiteren Anweisungen auf dem Bildschirm. Fertig.<br/>
                    <a data-fancybox="step5" href="/assets/images/step5.jpg"><img src="/assets/images/step5.jpg" style="max-width: 200px; max-height: 200px;" alt="Schritt 2"></a>
                </li>
            </ol>

            <p>
                <strong>Vielen Dank, dass du unsere Hilfe genutzt hast. Falls du weitere Ideen zur Verbesserung dieses Angebots hast, dann freuen wir uns riesig, wenn du uns unter hallo(at)kurzarbeit-einfach.de schreibst.</strong>
            </p>
        </div>

    </div>
</div>

<?php include('footer_beta_juri.php'); ?>
