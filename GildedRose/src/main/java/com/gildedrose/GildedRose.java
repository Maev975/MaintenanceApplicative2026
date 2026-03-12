package com.gildedrose;

class GildedRose {
    Item[] items;

    public GildedRose(Item[] items) {
        this.items = items;
    }
    /* 
    public void DoAlgeBrie () {
        for (int i = 0; i < items.length; i++) {
            if (!items[i].name.equals("Aged Brie")
                    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                if (items[i].quality > 0) {
                    if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                        items[i].quality = items[i].quality - 1;
                    }
                }
            } else {
                if (items[i].quality < 50) {
                    items[i].quality = items[i].quality + 1;

                    if (items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].sellIn < 11) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }

                        if (items[i].sellIn < 6) {
                            if (items[i].quality < 50) {
                                items[i].quality = items[i].quality + 1;
                            }
                        }
                    }
                }
            }

            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].sellIn = items[i].sellIn - 1;
            }

            if (items[i].sellIn < 0) {
                if (!items[i].name.equals("Aged Brie")) {
                    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
                        if (items[i].quality > 0) {
                            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                                items[i].quality = items[i].quality - 1;
                            }
                        }
                    } else {
                        items[i].quality = items[i].quality - items[i].quality;
                    }
                } else {
                    if (items[i].quality < 50) {
                        items[i].quality = items[i].quality + 1;
                    }
                }
            }
        }

    }
*/
    public void updateQuality() {
        for (int i = 0; i < items.length; i++) {
            Item item = items[i];

            switch (item.name) {
                case "Aged Brie":
                    item.quality = Math.min(50, item.quality + 1);
                    break;

                case "Backstage passes to a TAFKAL80ETC concert":
                    item.quality = Math.min(50, item.quality + 1);
                    if (item.sellIn < 11) {
                        item.quality = Math.min(50, item.quality + 1);
                    }
                    if (item.sellIn < 6) {
                        item.quality = Math.min(50, item.quality + 1);
                    }
                    break;

                case "Sulfuras, Hand of Ragnaros":
                    break;

                default:
                    if (item.quality > 0) {
                        item.quality = Math.max(0, item.quality - 1);
                    }
                    break;
            }

            if (!item.name.equals("Sulfuras, Hand of Ragnaros")) {
                item.sellIn = item.sellIn - 1;
            }

            if (item.sellIn < 0) {
                switch (item.name) {
                    case "Aged Brie":
                        item.quality = Math.min(50, item.quality + 1);
                        break;

                    case "Backstage passes to a TAFKAL80ETC concert":
                        item.quality = 0;
                        break;

                    case "Sulfuras, Hand of Ragnaros":
                        break;

                    default:
                        if (item.quality > 0) {
                            item.quality = Math.max(0, item.quality - 1);
                        }
                        break;
                }
            }
        }
    }
}
